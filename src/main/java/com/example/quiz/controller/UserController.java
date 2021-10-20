package com.example.quiz.controller;


import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping()
    public UserDTO getUserInfo(Principal principal)throws Exception{
        String username = principal.getName();
        return service.getUserByUsername(username);
    }
    @GetMapping("/login")
    public String  showLoginPage(){
        return "login";
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
