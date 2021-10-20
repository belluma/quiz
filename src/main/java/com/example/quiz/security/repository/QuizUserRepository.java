package com.example.quiz.security.repository;

import com.example.quiz.model.DB.QuizUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizUser, String> {
    public Optional<QuizUser> findByUsername(String username);

}
