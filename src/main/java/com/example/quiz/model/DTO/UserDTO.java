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
    private String email;
    private String password;
    private List<Highscore> highscores;

    public UserDTO(Integer id, String username, List<Highscore> highscores) {
        this.id = id;
        this.username = username;
        this.highscores = highscores;
    }

}
