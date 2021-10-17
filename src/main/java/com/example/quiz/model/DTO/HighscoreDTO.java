package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighscoreDTO extends Highscore {

    private Integer id;
    private User user;
    private int score;
}
