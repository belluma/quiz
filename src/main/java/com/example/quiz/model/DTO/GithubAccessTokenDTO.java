package com.example.quiz.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubAccessTokenDTO {

    @JsonProperty("access_token")
    private String accessToken;
}
