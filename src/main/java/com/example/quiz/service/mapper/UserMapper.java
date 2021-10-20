package com.example.quiz.service.mapper;

import com.example.quiz.model.DB.QuizUser;
import com.example.quiz.model.DTO.UserDTO;

public class UserMapper {

    public QuizUser mapUser(UserDTO user){
        return new QuizUser( user.getUsername(), user.getEmail(), user.getPassword(), user.isOnline(),  user.getHighscores());
    }

    public UserDTO mapUserAndConcealData(QuizUser quizUser){
        return new UserDTO( quizUser.getUsername(), quizUser.isOnline(), quizUser.getHighscores() );
    }

}
