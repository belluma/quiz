package com.example.quiz.model.DB;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Builder
public class QuizUser {

    @Id
    private String username;
    private String email;
    private String password;
    private boolean isOnline;

    @OneToMany
    @ToString.Exclude
    private List<Highscore> highscores;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizUser quizUser = (QuizUser) o;
        return username != null && Objects.equals(username, quizUser.username);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
