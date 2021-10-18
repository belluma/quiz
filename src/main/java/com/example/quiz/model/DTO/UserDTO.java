package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return isOnline == userDTO.isOnline && Objects.equals(id, userDTO.id) && Objects.equals(username, userDTO.username) && Objects.equals(email, userDTO.email) && Objects.equals(password, userDTO.password) && Objects.equals(highscores, userDTO.highscores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, username, email, password, isOnline, highscores);
    }
}
