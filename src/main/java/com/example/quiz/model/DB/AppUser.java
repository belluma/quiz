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
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Builder
public class AppUser {

    @Id
    private String username;
    private String email;
    private String password;
    private boolean isOnline;

    @OneToMany
    @ToString.Exclude
    private List<Highscore> highscores;

    public AppUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + username + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return username != null && Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
