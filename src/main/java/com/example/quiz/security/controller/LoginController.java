package com.example.quiz.security.controller;

import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.service.JWTUtilService;
import com.example.quiz.security.service.UserAuthService;
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
    private final UserAuthService userAuthService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTUtilService jwtService, UserAuthService userAuthService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
    }

    @PostMapping
    public String login(@RequestBody UserDTO user){
        this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return jwtService.createToken(new HashMap<>(), user.getUsername());
    }

    //temporary to get test running
    @PostMapping("signup")
    public UserDTO signup(@RequestBody UserDTO user){
        return userAuthService.signup(user);
    }
}
