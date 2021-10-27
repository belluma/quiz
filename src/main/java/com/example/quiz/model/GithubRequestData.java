package com.example.quiz.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@RequiredArgsConstructor
public class GithubRequestData {

//    @Value("${github.client_id}")
    private final String client_id;
//    @Value("${github.client_secret}")
    private final String client_secret;
    private  final String redirect_uri = "http://localhost:3000/login";
    private final String code;

}
