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

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtService;
    private final UserAuthService userAuthService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtilService jwtService, UserAuthService userAuthService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user){
        this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtService.createToken(new HashMap<>(), user.getUsername());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDTO user)throws IllegalArgumentException, AuthenticationException {
        return userAuthService.signup(user);
    }


}
