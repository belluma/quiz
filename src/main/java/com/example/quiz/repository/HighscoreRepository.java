package com.example.quiz.repository;

import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.Quizcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighscoreRepository extends JpaRepository<Highscore, Integer> {

}
