package com.example.quiz.controller;


import com.example.quiz.model.Quizcard;
import com.example.quiz.model.QuizcardDTO;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quizcard> getAllCards() {
        return quizService.getAllCards();
    }

    @PostMapping("/new")
    public Quizcard createNewCard(@RequestBody QuizcardDTO quizcard)  {
        return quizService.createQuizcard(quizcard);
    }

    @PostMapping
    public Boolean validateAnswer(@RequestBody QuizcardDTO quizcard) {
        return quizService.validateQuizcardAnswer(quizcard);
    }


}
