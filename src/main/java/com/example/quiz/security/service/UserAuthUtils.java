package com.example.quiz.security.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthUtils {

     void validatePassword(String password) throws IllegalArgumentException {
        //TODO  implement password validator
        if (password.length() < 3) {
            throw new IllegalArgumentException("Password too short!");
        }
    }

     void validateUsername(String username) throws IllegalArgumentException {
        // TODO implement logic for username validation
        int min = 4;
        int max = 20;
        if (username.length() < min || username.length() > max) {
            throw new IllegalArgumentException(String.format("Username must be longer than %d and shorter than %d characters", min, max));
        }
    }

     void validateEmail(String email) throws IllegalArgumentException {
        //TODO implement email validation
        if (email.length() < 1) throw new IllegalArgumentException("invalid email");
    }
     HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token);
        return headers;
    }

     HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
