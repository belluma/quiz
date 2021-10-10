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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quizcard quizCard = (Quizcard) o;
        return id == quizCard.id && Objects.equals(question, quizCard.question) && Objects.equals(choices, quizCard.choices) && Objects.equals(answerIndices, quizCard.answerIndices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, choices, answerIndices);
    }

    @Id
    @GeneratedValue
    private int id;


    private String question;
    @ElementCollection
    private List<String> choices;
    @ElementCollection
    private List<Integer> answerIndices;


}
