package com.example.quiz.controller;


import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.security.service.QuizUserService;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    @Autowired
    private final QuizUserService quizUserService;

    public QuizController(QuizService quizService, QuizUserService quizUserService) {
        this.quizService = quizService;
        this.quizUserService = quizUserService;
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


}
