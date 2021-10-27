package com.example.quiz.security.service;
import com.example.quiz.model.DTO.GithubUserDTO;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.model.GithubRequestData;
import com.example.quiz.model.exception.UserAlreadyExistsException;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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


@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;
    private final JWTUtilService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper mapper = new UserMapper();
    private final RestTemplate restTemplate;
    @Value("${github.client_id}")
    private  String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Autowired
    public UserAuthService(UserRepository repository, JWTUtilService jwtService, RestTemplate restTemplate) {
        this.repository = repository;
        this.jwtService = jwtService;
//        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
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
        validatePassword(user.getPassword());
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
    }

    private void validatePassword(String password) throws IllegalArgumentException {
        //TODO  implement password validator
        if (password.length() < 3) {
            throw new IllegalArgumentException("Password too short!");
        }
    }

    private void validateUsername(String username) throws IllegalArgumentException {
        // TODO implement logic for username validation
        int min = 4;
        int max = 20;
        if (username.length() < min || username.length() > max) {
            throw new IllegalArgumentException(String.format("Username must be longer than %d and shorter than %d characters", min, max));
        }
    }

    private void validateEmail(String email) throws IllegalArgumentException {
        //TODO implement email validation
        if (email.length() < 1) throw new IllegalArgumentException("invalid email");
    }

    public String getTokenFromGithub(String code) {
        GithubRequestData requestData = new GithubRequestData(client_id, client_secret, code);
        System.err.println(requestData);

        ResponseEntity<String> token = restTemplate.exchange("https://github.com/login/oauth/access_token/", HttpMethod.POST, new HttpEntity<>(requestData), String.class);
        System.err.println(token);
        return parseGithubToken(token.getBody());
//        return getUsernameFromGithub(parseGithubToken(token.getBody()));
//        String username =
//        return
    }

    public String getUsernameFromGithub(String code) {
        String token = getTokenFromGithub(code);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token);
        System.err.println(token);
        ResponseEntity<GithubUserDTO> response =  restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, new HttpEntity<>(headers), GithubUserDTO.class);
        System.err.println(response.getBody().getLogin());
        return response.getBody().getLogin();
//        System.err.println(response.getBody());
//        return "abc";
    }
    private String parseGithubToken(String responseData){
        int start = 13;
        return responseData.substring(start, start + 40);
    }

}
