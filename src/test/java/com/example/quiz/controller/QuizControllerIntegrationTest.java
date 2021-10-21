package com.example.quiz.controller;

import com.example.quiz.model.DB.QuizUser;
import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.security.repository.QuizUserRepository;
import com.example.quiz.service.mapper.QuizcardMapper;
import com.example.quiz.service.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
public class QuizControllerIntegrationTest {

    @Autowired
    QuizController quizController;
    @Autowired
    GlobalExceptionHandler exceptionHandler;
    @Autowired
    QuizRepository repository;
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    QuizUserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    QuizcardMapper mapper = new QuizcardMapper();
    UserMapper userMapper = new UserMapper();

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

    QuizcardDTO card = new QuizcardDTO(1, "question", List.of("answer"), List.of(0));
    QuizcardDTO cardWithConcealedAnswers = new QuizcardDTO(1, "question", List.of("answer"), List.of());

    @BeforeEach
    public void clearDB() {
        repository.deleteAll();
    }


    @Test
    void test() {
        assertTrue(container.isRunning());
    }

    @Test
    void testGetAllReturns204() {
        HttpHeaders headers = createHeadersWithJwtAuth();
        System.err.println(headers);
        System.err.println(restTemplate.exchange("/api/quiz", HttpMethod.GET, new HttpEntity<>(headers), QuizcardDTO[].class));
        Exception ex = assertThrows(NoSuchElementException.class, () -> restTemplate.exchange("/api/quiz", HttpMethod.GET, new HttpEntity<>(headers), QuizcardDTO[].class));
        System.err.println(ex);
        assertThat(ex.getMessage(), equalTo("No quizcards created yet"));
    }

    @Test
    void testCreateNewCardAddsOneCardToDb() {
        HttpHeaders headers = createHeadersWithJwtAuth();
        ResponseEntity<QuizcardDTO> response = restTemplate.exchange("/api/quiz/new", HttpMethod.POST, new HttpEntity<>(headers), QuizcardDTO.class);
        System.err.println(response);
        //get correct response from post method
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(cardWithConcealedAnswers));
        //check that element has been addded to db
        ResponseEntity<QuizcardDTO[]> dbContents = restTemplate.exchange("/api/quiz", HttpMethod.GET, new HttpEntity<>(headers), QuizcardDTO[].class);
        QuizcardDTO[] expected = {cardWithConcealedAnswers};
        assertThat(dbContents.getStatusCode(), is(HttpStatus.OK));
//        assertIterableEquals(expected, dbContents.getBody());
    }

    @Test
    void createNewCardReturns406OnWrongInput() {
        HttpHeaders headers = createHeadersWithJwtAuth();
        QuizcardDTO card1 = new QuizcardDTO(1, "", List.of("answer"), List.of(0));
        QuizcardDTO card2 = new QuizcardDTO(1, "question", List.of(), List.of(0));
        QuizcardDTO card3 = new QuizcardDTO(1, "question", List.of("answer"), List.of());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> restTemplate.exchange("/api/quiz/new", HttpMethod.POST,  new HttpEntity<>(card1, headers), QuizcardDTO.class));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> restTemplate.exchange("/api/quiz/new", HttpMethod.POST,  new HttpEntity<>(card2, headers), QuizcardDTO.class));
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> restTemplate.exchange("/api/quiz/new", HttpMethod.POST,  new HttpEntity<>(card3, headers), QuizcardDTO.class));
        testGetAllReturns204();
    }

    private HttpHeaders createHeadersWithJwtAuth() {

        QuizUser user = new QuizUser("user", passwordEncoder.encode("1234"));
        userRepository.save(user);
        UserDTO loginData = new UserDTO("user", "1234");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getBody());
        return headers;


//        //        QuizUser user = new QuizUser("user", passwordEncoder.encode("a"));
////        System.err.println(userRepository.save(user));
//        UserDTO loginData = new UserDTO("user", "a");
//        QuizUser user = userMapper.mapUser(loginData);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
////        UserDTO loginData = createUser();
//
//        userRepository.save(user);
//        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
//        System.err.println(response.getBody());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(response.getBody());
//        return headers;
    }
    private UserDTO createUser() {
        return new UserDTO("username", "a@b.c", passwordEncoder.encode("1234"));
    }
}
