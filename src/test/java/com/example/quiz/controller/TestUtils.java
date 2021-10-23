package com.example.quiz.controller;

import com.example.quiz.model.DB.AppUser;
import com.example.quiz.model.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestUtils {

    @Autowired
    PasswordEncoder passwordEncoder;

    public AppUser createMockUser() {
        return new AppUser("username", passwordEncoder.encode("1234"));
    }
    public static UserDTO createMockUserWithCleartextPassword() {
        return new UserDTO("username", "a@b.c", "1234");
    }

}
