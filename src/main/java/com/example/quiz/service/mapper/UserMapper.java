package com.example.quiz.service.mapper;

import com.example.quiz.model.DB.User;
import com.example.quiz.model.DTO.UserDTO;

public class UserMapper {

    public User mapUser(UserDTO user){
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.isOnline(),  user.getHighscores());
    }

    public UserDTO mapUserAndConcealData(User user){
        return new UserDTO(user.getId(), user.getUsername(),user.isOnline(), user.getHighscores() );
    }

}
