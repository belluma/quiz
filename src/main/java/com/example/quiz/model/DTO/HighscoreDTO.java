package com.example.quiz.model.DTO;


import com.example.quiz.model.DB.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HighscoreDTO  {

    private Integer id;
    private AppUser appUser;
    private int score;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HighscoreDTO highscore = (HighscoreDTO) o;
        return Objects.equals(id, highscore.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
