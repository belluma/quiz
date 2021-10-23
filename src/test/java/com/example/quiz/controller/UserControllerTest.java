package com.example.quiz.controller;

import com.example.quiz.model.DB.AppUser;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.repository.UserRepository;
import com.example.quiz.security.service.JWTUtilService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "jwt.secret=secret")
class UserControllerTest{

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtilService jwtUtilService;
    @Autowired
    private TestRestTemplate restTemplate;

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
        repository.deleteAll();
    }




    @Test
void getLoggedInUserWithValidToken(){
    HttpHeaders headers = getHttpHeadersWithJWT();
    ResponseEntity<UserDTO> response = restTemplate.exchange("/api/user", HttpMethod.GET, new HttpEntity<>(headers), UserDTO.class);
    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody().getUsername(), is("username"));
}
@Test
void get403WithInvalidToken(){
    HttpHeaders headers = getHttpHeadersWithJWT();
    headers.setBearerAuth("gibberish.invalid.token");
    ResponseEntity<UserDTO> response = restTemplate.exchange("/api/user", HttpMethod.GET, new HttpEntity<>(headers), UserDTO.class);
    assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
}
@Test
void get403WithExpiredToken(){
    HttpHeaders headers = getHttpHeadersWithJWT();
    ReflectionTestUtils.setField(jwtUtilService, "duration", 1);
    headers.setBearerAuth(jwtUtilService.createToken(new HashMap<>(), "user"));
    ResponseEntity<UserDTO> response = restTemplate.exchange("/api/user", HttpMethod.GET, new HttpEntity<>(headers), UserDTO.class);
    assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
}



    private HttpHeaders getHttpHeadersWithJWT() {
        AppUser user = new AppUser("username", passwordEncoder.encode("1234"));
        repository.save(user);
        user.setPassword("1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", user, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getBody());
        return headers;
    }
}
