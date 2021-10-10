package com.example.quiz.controller;


import com.example.quiz.model.Quizcard;
import com.example.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService service;

    public QuizController(QuizService quizService){
        this.service = quizService;
    }

    @GetMapping
    public ResponseEntity<Quizcard> getAllCards(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<Quizcard> createNewCard(){
        return new ResponseEntity(HttpStatus.OK);
    }



}
