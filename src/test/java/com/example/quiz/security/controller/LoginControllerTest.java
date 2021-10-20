package com.example.quiz.security.controller;

import com.example.quiz.controller.GlobalExceptionHandler;
import com.example.quiz.model.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @Autowired
    private LoginController loginController;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

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

    @BeforeEach
    private void addUserToDb() {

        restTemplate.postForEntity("/auth/login", createUser(), String.class);
    }

    @Test
    void login() {
        UserDTO user = createUser();
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
    @Order(2)
    void loginFailsWithWrongCredentials() {
        UserDTO user = createUser();
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    private static UserDTO createUser() {
        return new UserDTO("username", "a@b.c", "$2a$10$FyP28ouTokqY2snJndKihex0qPn8VLcJtVcjq.7/c3J9RdAvXUxWC");
    }
}
