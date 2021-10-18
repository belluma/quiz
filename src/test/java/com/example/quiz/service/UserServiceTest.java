package com.example.quiz.service;

import com.example.quiz.model.DB.User;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@Service
@RequiredArgsConstructor
class UserServiceTest {

    @Autowired
    UserRepository repository = mock(UserRepository.class);

    UserMapper mapper = new UserMapper();

    private final UserService service = new UserService(repository);

    @Test
    void getAllUsers() {
        User user = buildUser(1);
        when(repository.findAll()).thenReturn(List.of(user));
        List<UserDTO> actual = service.getAllUsers();
        assertIterableEquals(List.of(mapper.mapUserAndConcealData(user)), actual);
        verify(repository).findAll();
    }

    @Test
    void getAllUsersOnline() {
        User user = buildUser(1);
        User user2 = buildUser(2);
        user2.setOnline(true);
        when(repository.findAll()).thenReturn(List.of(user, user2));
        List<UserDTO> actual = service.getAllUsersOnline();
        assertIterableEquals(List.of(mapper.mapUserAndConcealData(user2)), actual);
        verify(repository).findAll();
    }

    @Test
    void getUserById() {
        User user = buildUser(1);
        when(repository.findById(1)).thenReturn(Optional.of(user));
        UserDTO actual = service.getUserById(1);
        assertThat(actual).isEqualTo(mapper.mapUserAndConcealData(user));
        verify(repository).findById(1);
    }

    @Test
    void getUserByName() {
        User user = buildUser(1);
        User user2 = buildUser(2);
        when(repository.findByUsername("user")).thenReturn(List.of(user, user2));
        List<UserDTO> expected = List.of(mapper.mapUserAndConcealData(user), mapper.mapUserAndConcealData(user2));
        List<UserDTO> actual = service.getUsersByName("user");
        assertIterableEquals(actual, expected);
        verify(repository).findByUsername("user");
    }

    @Test
    void signup() {
    }

    @Test
    void login() {
    }

    private User buildUser(Integer id) {
        return User.builder()
                .id(id)
                .username("John")
                .email("user@email.com")
                .password("password")
                .isOnline(false)
                .build();
    }

}
