package com.example.quiz.security.controller;

import com.example.quiz.controller.GlobalExceptionHandler;
import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.QuizUser;
import com.example.quiz.security.repository.QuizUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginControllerTest {

    @Autowired
    LoginController loginController;
    @Autowired
    GlobalExceptionHandler exceptionHandler;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("quiz")
            .withUsername("quiz")
            .withPassword("quiz");

    @DynamicPropertySource //https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Order(1)
    void createuser(){

    }

    @Test
    void login() {

        repository.save(createUser());

        QuizUser user = createUser();
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Claims body = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(response.getBody()).getBody();
        assertThat(body.getSubject(), equalTo("user"));
    }

    @Test
    void loginFailsWithWrongCredentials() {

        repository.save(createUser());

        QuizUser user = createUser();
        user.setPassword("123");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    private QuizUser createUser() {
        return new QuizUser("username", "a@b.c", "$2a$10$FyP28ouTokqY2snJndKihex0qPn8VLcJtVcjq.7/c3J9RdAvXUxWC ", false, List.of(new Highscore()));
    }
}
