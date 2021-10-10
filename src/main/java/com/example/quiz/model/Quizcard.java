package com.example.quiz.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="questions")
public class Quizcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String question;
    @ElementCollection
    private List<String> choices;
    @ElementCollection
    private List<Integer> answerIndices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quizcard todo = (Quizcard) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
