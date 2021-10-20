package com.example.quiz.security.controller;

import com.example.quiz.model.DB.QuizUser;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.service.JWTUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTUtilService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public String login(@RequestBody UserDTO user){
        this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return jwtService.createToken(new HashMap<>(), user.getUsername());
    }
}
