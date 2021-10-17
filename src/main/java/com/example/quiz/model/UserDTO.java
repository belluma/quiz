package com.example.quiz.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends User{

    private Integer id;

    private String username;
    private String email;
    private String password;
    private List<Highscore> highscores;
}
