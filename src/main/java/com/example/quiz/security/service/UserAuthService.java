package com.example.quiz.security.service;

import com.example.quiz.model.DTO.GithubAccessTokenDTO;
import com.example.quiz.model.DTO.GithubUserDTO;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.model.GithubRequestData;
import com.example.quiz.model.exception.GithubAuthException;
import com.example.quiz.model.exception.UserAlreadyExistsException;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.List;


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper mapper = new UserMapper();
    private final RestTemplate restTemplate;
    private final UserAuthUtils utils;
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_URL = "https://api.github.com/user";
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;

    @Autowired
    public UserAuthService(UserRepository repository, JWTUtilService jwtService, RestTemplate restTemplate, UserAuthUtils utils) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
        this.utils = utils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username)
                .map(appUser -> User
                        .withUsername(username)
                        .password(appUser.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist: " + username));
    }

    public String signup(UserDTO user) throws AuthenticationException, IllegalArgumentException {
        validateUserData(user);
        if (repository.findById(user.getUsername()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(mapper.mapUser(user));
            return jwtService.createToken(new HashMap<>(), user.getUsername());
        }
        throw new UserAlreadyExistsException(String.format("User with username %s already exists", user.getUsername()));
    }

    private void validateUserData(UserDTO user) throws IllegalArgumentException {
        utils.validatePassword(user.getPassword());
        utils.validateUsername(user.getUsername());
        utils.validateEmail(user.getEmail());
    }


    private String getTokenFromGithub(String code) throws GithubAuthException {
        GithubRequestData requestData = new GithubRequestData(client_id, client_secret, code);
        HttpHeaders headers = jsonHeaders();
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
        HttpHeaders headers = authHeaders(token);
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

    private HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token);
        return headers;
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
