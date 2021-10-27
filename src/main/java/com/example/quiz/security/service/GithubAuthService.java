package com.example.quiz.security.service;

import com.example.quiz.model.DTO.GithubAccessTokenDTO;
import com.example.quiz.model.DTO.GithubUserDTO;
import com.example.quiz.model.GithubRequestData;
import com.example.quiz.model.exception.GithubAuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubAuthService {

    private final UserAuthUtils utils;
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_URL = "https://api.github.com/user";
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    private final RestTemplate restTemplate;

    public GithubAuthService(UserAuthUtils utils, RestTemplate restTemplate) {
        this.utils = utils;
        this.restTemplate = restTemplate;
    }


    private String getTokenFromGithub(String code) throws GithubAuthException {
        GithubRequestData requestData = new GithubRequestData(client_id, client_secret, code);
        HttpHeaders headers = utils.jsonHeaders();
        ResponseEntity<GithubAccessTokenDTO> response = restTemplate.exchange(
                GITHUB_TOKEN_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestData, headers),
                GithubAccessTokenDTO.class);
        if (response.getBody() != null) {
            return response.getBody().getAccessToken();
        }
        throw new GithubAuthException("Error while retrieving authentication token from Github! Response Body is null!");
    }

    public String getUsernameFromGithub(String code) throws GithubAuthException {
        String token = getTokenFromGithub(code);
        HttpHeaders headers = utils.authHeaders(token);
        ResponseEntity<GithubUserDTO> response = restTemplate.exchange(
                GITHUB_USER_URL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GithubUserDTO.class);
        if (response.getBody() == null) {
            throw new GithubAuthException("Error while retrieving username from Github! Response Body is null!");
        }
        return response.getBody().getLogin();

    }
}
