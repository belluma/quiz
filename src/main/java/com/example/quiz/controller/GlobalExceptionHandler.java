package com.example.quiz.controller;


import com.example.quiz.model.CustomError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        String errorMessage = ex.getMessage();
        if (errorMessage == null) errorMessage = ex.toString();
        CustomError message = new CustomError(new Date(), errorMessage);

        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex) {
        String errorMessage = ex.getMessage();
        if (errorMessage == null) errorMessage = ex.toString();
        CustomError message = new CustomError(new Date(), errorMessage);
        System.out.println(errorMessage.equals(("No quizcards created yet")));
        if (errorMessage.equals("No quizcards created yet")) {
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
