package com.example.quiz.model.DB;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "highscores")
@Builder
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Highscore highscore = (Highscore) o;
        return id != null && Objects.equals(id, highscore.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
