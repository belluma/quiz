package com.example.quiz.repository;

import com.example.quiz.model.Quizcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quizcard, Integer> {

}
