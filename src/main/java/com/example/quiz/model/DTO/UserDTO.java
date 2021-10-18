package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDTO user = (UserDTO) o;
        return Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
