package com.example.quiz.controller;


import com.example.quiz.model.CustomError;
import com.example.quiz.model.NoCardsCreatedYetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoCardsCreatedYetException.class})
    public ResponseEntity<Object> handleNoCardsCreatedYetException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({BadCredentialsException.class,})
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleAllTheRest(Exception ex) {
        CustomError message = new CustomError(ex);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }
}
