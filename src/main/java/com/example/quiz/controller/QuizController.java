package com.example.quiz.controller;


import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.security.model.UserDTO;
import com.example.quiz.security.service.QuizUserService;
import com.example.quiz.security.service.UserService;
import com.example.quiz.service.QuizService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    @Autowired
    private final UserService userService;

    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping
    public List<QuizcardDTO> getAllCards() {
        return quizService.getAllCards();
    }

    @PostMapping("/new")
    public QuizcardDTO createNewCard(@RequestBody QuizcardDTO quizcard)  {
        return quizService.createQuizcard(quizcard);
    }

    @PostMapping
    public Boolean validateAnswer(@RequestBody QuizcardDTO quizcard) {
        return quizService.validateQuizcardAnswer(quizcard);
    }

    @GetMapping("/user")
    public UserDTO getUserInfo(Principal principal){
        String username = principal.getName();
        return userService.getUserByUsername(username);//.orElseThrow;
    }
}
