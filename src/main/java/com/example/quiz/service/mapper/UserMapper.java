package com.example.quiz.service.mapper;

import com.example.quiz.model.DB.AppUser;
import com.example.quiz.model.DTO.UserDTO;

public class UserMapper {

    public AppUser mapUser(UserDTO user){
        return new AppUser( user.getUsername(), user.getEmail(), user.getPassword(), user.isOnline(),  user.getHighscores());
    }

    public UserDTO mapUserAndConcealData(AppUser appUser){
        return new UserDTO( appUser.getUsername(), appUser.isOnline(), appUser.getHighscores() );
    }

}
