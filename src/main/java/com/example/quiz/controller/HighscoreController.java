package com.example.quiz.controller;


import com.example.quiz.model.DTO.HighscoreDTO;
import com.example.quiz.service.HighscoreService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/highscore")
public class HighscoreController {

    private final HighscoreService service;

    public HighscoreController(HighscoreService service) {
        this.service = service;}

    public List<HighscoreDTO>getHighscores(){
        return service.getHighscores();
    }
    public HighscoreDTO saveHighscore(@RequestBody HighscoreDTO highscore){
        return service.saveHighscore(highscore);
    }
}
