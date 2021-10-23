package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.Highscore;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {


    private String username;
    private String email;
    private String password;

    private boolean isOnline;
    private List<Highscore> highscores;

    public UserDTO(String username,boolean isOnline, List<Highscore> highscores) {
        this.username = username;
        this.isOnline = isOnline;
        this.highscores = highscores;
    }
 public UserDTO(String username,String email, String password) {
        this.username = username;
        this.email = email;
     this.password = password;
    }
public UserDTO(String username, String password) {
        this.username = username;
     this.password = password;
    }


    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDTO user = (UserDTO) o;
        return Objects.equals(username, user.getUsername());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
