package com.example.quiz.controller;


import com.example.quiz.model.Quizcard;
import com.example.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<Quizcard> getAllCards() {
        return new ResponseEntity(quizService.getAllCards(), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<Quizcard> createNewCard(@RequestBody Quizcard quizcard) {
        return new ResponseEntity(quizService.createQuizcard(quizcard), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Boolean> validateAnswer(@PathVariable Integer id) {
        System.out.println(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
