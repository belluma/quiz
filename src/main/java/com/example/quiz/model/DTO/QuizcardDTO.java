package com.example.quiz.model.DTO;

import com.example.quiz.model.DB.Quizcard;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizcardDTO extends Quizcard {

    private Integer id;
    private String question;
    private List<String> choices;
    private List<Integer> answerIndices;

    public QuizcardDTO(Integer id, String question, List<String> choices){
        this.id = id;
        this.question = question;
        this.choices = choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quizcard quizcard = (Quizcard) o;
        return Objects.equals(id, quizcard.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
