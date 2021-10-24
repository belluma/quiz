package com.example.quiz.security.service;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.model.exception.UserAlreadyExistsException;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;
    private final JWTUtilService jwtService;

    private final UserMapper mapper = new UserMapper();

    public UserAuthService(UserRepository repository, JWTUtilService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username)
                .map(appUser -> User
                        .withUsername(username)
                        .password(appUser.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist: " + username));
    }

    public String signup(UserDTO user) throws AuthenticationException, IllegalArgumentException {
        validateUserData(user);
        if (repository.findById(user.getUsername()).isEmpty()) {
            repository.save(mapper.mapUser(user));
            return jwtService.createToken(new HashMap<>(), user.getUsername());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", user.getUsername()));
    }
    private void validateUserData(UserDTO user) throws IllegalArgumentException{
        validatePassword(user.getPassword());
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
    }
    private void validatePassword(String password) throws IllegalArgumentException{
        //TODO  implement password validator
        if(password.length() < 3) {
            throw new IllegalArgumentException("Password too short!");}
    }

    private void validateUsername(String username) throws IllegalArgumentException{
        // TODO implement logic for username validation
        int min = 4;
        int max = 20;
        if(username.length() < min || username.length() > max){
            throw new IllegalArgumentException(String.format("Username must be longer than %d and shorter than %d characters", min, max));
        }
    }

    private void validateEmail(String email) throws IllegalArgumentException{
        //TODO implement email validation
        if(email.length() < 1) throw new IllegalArgumentException("invalid email");
    }
}
