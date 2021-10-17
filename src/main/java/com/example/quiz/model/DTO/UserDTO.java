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

    private boolean isOnline;
    private List<Highscore> highscores;

    public UserDTO(Integer id, String username,boolean isOnline, List<Highscore> highscores) {
        this.id = id;
        this.username = username;

        this.isOnline = isOnline;
        this.highscores = highscores;
    }


    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}
