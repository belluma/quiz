package com.example.quiz.security.controller;

import com.example.quiz.controller.GlobalExceptionHandler;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;// = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    private final UserMapper mapper = new UserMapper();

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${github.client.id}")
    private String clientId;

    @DynamicPropertySource //https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("quiz")
            .withUsername("quiz")
            .withPassword("quiz");

    @AfterEach
    public void clearDb() {
        userRepository.deleteAll();
    }

    @Test
    void login() {
        UserDTO user = createUser();
        userRepository.save(mapper.mapUser(user));
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        Claims body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(response.getBody())
                .getBody();
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(body.getSubject(), equalTo("username"));
    }

    @Test
    void loginFailsWithWrongPassword() {
        UserDTO user = createUser();
        userRepository.save(mapper.mapUser(user));
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void loginFailsWithWrongUsername() {
        UserDTO user = createUser();
        user.setPassword("1234");
        user.setUsername("wrong_username");
        userRepository.save(mapper.mapUser(user));
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    void signupSavesUserAndReturnsLogin(){
        UserDTO user = createUser();
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        Claims body = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(response.getBody())
                .getBody();
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(body.getSubject(), equalTo("username"));
        assertThat(userRepository.findByUsername(user.getUsername()), is(Optional.of(mapper.mapUser(user))));
    }
    @Test
    void signupFailsWhenUsernameAlreadyRegistered(){
        UserDTO user = createUser();
        userRepository.save(mapper.mapUser(user));
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }
    @Test
    void signupFailsWhenLackingInformation(){
        UserDTO user = new UserDTO("username", "", passwordEncoder.encode("1234"));
        UserDTO user2 = new UserDTO("username", "a@b.c");
        UserDTO user3 = new UserDTO("", "a@b.c", passwordEncoder.encode("1234"));
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/signup", user, String.class);
        ResponseEntity<String> response2 = restTemplate.postForEntity("/auth/signup", user2, String.class);
        ResponseEntity<String> response3 = restTemplate.postForEntity("/auth/signup", user3, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response2.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
        assertThat(response3.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    void retrieveClientId(){
        ResponseEntity<String> response = restTemplate.getForEntity("/auth/github/client_id", String.class);
        assertThat(response.getBody(), is(clientId));
    }

    @Test
    void loginWithGithubReturnsErrorWithWrongCode(){
        ResponseEntity<String> response = restTemplate.getForEntity("/auth/github/1234", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }


    private UserDTO createUser() {
        return new UserDTO("username", "a@b.c", passwordEncoder.encode("1234"));
    }
}
