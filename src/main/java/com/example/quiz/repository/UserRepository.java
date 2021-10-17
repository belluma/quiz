package com.example.quiz.repository;

import com.example.quiz.model.DB.Quizcard;
import com.example.quiz.model.DB.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByUsername(String username);

}
