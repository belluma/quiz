package com.example.quiz.security.service;

import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.model.exception.UserAlreadyExistsException;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashMap;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper mapper = new UserMapper();
    private final UserAuthUtils utils;


    @Autowired
    public UserAuthService(UserRepository repository, JWTUtilService jwtService, UserAuthUtils utils) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username)
                .map(appUser -> User
                        .withUsername(username)
                        .password(appUser.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist: " + username));
    }

    public String signup(UserDTO user) throws AuthenticationException, IllegalArgumentException {
        validateUserData(user);
        if (repository.findById(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(mapper.mapUser(user));
            return jwtService.createToken(new HashMap<>(), user.getUsername());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", user.getUsername()));
    }

    private void validateUserData(UserDTO user) throws IllegalArgumentException {
        utils.validatePassword(user.getPassword());
        utils.validateUsername(user.getUsername());
        utils.validateEmail(user.getEmail());
    }

}
