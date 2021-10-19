package com.example.quiz.controller;


import com.example.quiz.security.model.UserDTO;
import com.example.quiz.security.service.QuizUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private final QuizUserService service;

    public UserController(QuizUserService service) {
        this.service = service;
    }
    @GetMapping("/me")
    public UserDTO getUserInfo(Principal principal)throws Exception{
        String username = principal.getName();
        return service.getUserByUsername(username);
    }
//    @GetMapping
//    public List<UserDTO> getAllUsers() {
//        return service.getAllUsers();
//    }
//
//    @GetMapping("/online")
//    public List<UserDTO> getAllUsersOnline() {
//        return service.getAllUsersOnline();
//    }
//
//    @GetMapping("{id}")
//    public UserDTO getUserById(@PathVariable Integer id) {
//        return service.getUserById(id);
//    }
//
//    @GetMapping("{name}")
//    public List<UserDTO> getUsersByName(@PathVariable String nameQuery) {
//        return service.getUsersByName(nameQuery);
//    }
//
//    @PostMapping
//    public UserDTO login(UserDTO user) {
//        return service.login(user);
//    }
//
//    @PostMapping("signup")
//    public UserDTO signup(UserDTO user) {
//        return service.login(user);
//    }
}
