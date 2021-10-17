package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends User {

    private Integer id;

    private String username;
    private List<Highscore> highscores;
}
