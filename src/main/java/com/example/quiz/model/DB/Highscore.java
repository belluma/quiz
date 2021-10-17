package com.example.quiz.model.DB;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "highscores")
public class Highscore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;
    private int score;
    private final LocalDateTime date = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Highscore highscore = (Highscore) o;
        return score == highscore.score && Objects.equals(id, highscore.id) && Objects.equals(user, highscore.user) && Objects.equals(date, highscore.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, score, date);
    }
}
