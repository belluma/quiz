package com.example.quiz.security.service;

import com.example.quiz.model.DB.AppUser;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.model.exception.UserAlreadyExistsException;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Service
@RequiredArgsConstructor
class UserAuthServiceTest {

    @Autowired
    private UserRepository repository = mock(UserRepository.class);
    @Autowired
    private JWTUtilService jwtService = mock(JWTUtilService.class);
    private final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
    private final UserMapper mapper = new UserMapper();

    @Autowired
    private final UserAuthService service = new UserAuthService(repository, jwtService);



    @Test
    void loadUserByUsername() {
        AppUser user = new AppUser("username", "1234");
        when(repository.findById("username")).thenReturn(Optional.of(user));
        UserDetails expected = User
                .withUsername("username")
                .password("1234")
                .authorities("user")
                .build();
        assertThat(expected, is(service.loadUserByUsername("username")));
    }
  @Test
    void loadUserByUsernameThrowsWhenUserIsNotFound() throws Exception {
        when(repository.findById("username")).thenReturn(Optional.empty());
     Exception ex =  assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
      assertThat(ex.getMessage(), is("Username does not exist: username"));
    }

    @Test
    void signup() throws AuthenticationException {
        UserDTO user = new UserDTO("username", "a@b.d","1234");
        when(repository.findById("username")).thenReturn(Optional.empty());
        when(jwtService.createToken(new HashMap<>(), "username")).thenReturn("valid.jwt.token");
        String expected = "valid.jwt.token";
        assertThat(expected, is(service.signup(user)));
    }

    @Test
    void signupFailsWhenUsernameAlreadyRegistered(){
        UserDTO user = new UserDTO("username", "a@b.c", "1234");
        when(repository.findById("username")).thenReturn(Optional.of(mapper.mapUser(user)));
        Exception ex = assertThrows(UserAlreadyExistsException.class, () -> service.signup(user));
        assertThat(ex.getMessage(), is("User with username username already exists"));
    }
@Test
    void signupFailsWhenUsernameTooShort(){
    UserDTO user = new UserDTO("use", "a@b.c", "1234");
    when(repository.findById("use")).thenReturn(Optional.empty());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> service.signup(user));
    assertThat(ex.getMessage(), is("Username must be longer than 4 and shorter than 20 characters"));
    }
@Test
    void signupFailsWhenUsernameTooLong(){
    UserDTO user = new UserDTO("usernameIsMuchLongerThan20Characters", "a@b.c", "1234");
    when(repository.findById("username")).thenReturn(Optional.empty());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> service.signup(user));
    assertThat(ex.getMessage(), is("Username must be longer than 4 and shorter than 20 characters"));
    }
@Test
    void signupFailsWhenPasswordInvalid(){
    UserDTO user = new UserDTO("username", "a@b.c", "12");
    when(repository.findById("username")).thenReturn(Optional.empty());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> service.signup(user));
    assertThat(ex.getMessage(), is("Password too short!"));
    }
@Test
    void signupFailsWhenEmailInvalid(){
    UserDTO user = new UserDTO("username", "", "1234");
    when(repository.findById("username")).thenReturn(Optional.empty());
    Exception ex = assertThrows(IllegalArgumentException.class, () -> service.signup(user));
    assertThat(ex.getMessage(), is("invalid email"));
    }
}
