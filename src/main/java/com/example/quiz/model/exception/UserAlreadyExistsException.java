package com.example.quiz.model.exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {

    public UserAlreadyExistsException(final String message){
        super(message);
    }

}
