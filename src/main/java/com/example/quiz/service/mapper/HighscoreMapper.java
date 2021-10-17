package com.example.quiz.service.mapper;

import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DTO.HighscoreDTO;

public class HighscoreMapper {

    public Highscore mapHighscore(HighscoreDTO highscore) {
        return new Highscore(highscore.getId(), highscore.getUser(), highscore.getScore());
    }

    public HighscoreDTO mapHighscoreToDTO(Highscore highscore) {
        return new HighscoreDTO(highscore.getId(), highscore.getUser(), highscore.getScore());
    }
}
