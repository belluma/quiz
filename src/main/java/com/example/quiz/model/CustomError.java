package com.example.quiz.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CustomError {
    private LocalDateTime timeStamp;
    private String message;

    public CustomError(String message){
        this.timeStamp = LocalDateTime.now();
        this.message = message;
    }
public CustomError(){
        this.timeStamp = LocalDateTime.now();
        this.message = "Unknown Error";
    }
}
