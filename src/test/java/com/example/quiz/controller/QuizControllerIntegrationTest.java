package com.example.quiz.controller;

import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.service.mapper.QuizcardMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    QuizcardMapper mapper = new QuizcardMapper();

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

    @AfterEach
    public void clearDB(){
        repository.deleteAll();
    }


    @Test
    void test() {
        assertTrue(container.isRunning());
    }

    @Test
    void testGetAllReturns204() {
        Exception ex = assertThrows(NoSuchElementException.class, () -> restTemplate.exchange("/api/quiz",HttpMethod.GET, headers, QuizcardDTO.class));
        assertThat(ex.getMessage(), equalTo("No quizcards created yet"));
    }

    @Test
    void testCreateNewCardAddsOneCardToDb() {
        ResponseEntity<QuizcardDTO> response = restTemplate.exchange("/api/quiz/new", HttpMethod.POST, headers, QuizcardDTO.class);
       //get correct response from post method
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(cardWithConcealedAnswers));
        //check that element has been addded to db
        ResponseEntity<List<QuizcardDTO>> dbContents = restTemplate.exchange("/api/quiz", HttpMethod.GET, headers, QuizcardDTO.class);
        assertThat(dbContents.getStatusCode(), is(HttpStatus.OK));
        assertIterableEquals(dbContents.getBody(), List.of(equalTo(cardWithConcealedAnswers)));
    }

    @Test
    void createNewCardReturns406OnWrongInput() {
        QuizcardDTO card1 = new QuizcardDTO(1, "", List.of("answer"), List.of(0));
        QuizcardDTO card2 = new QuizcardDTO(1, "question", List.of(), List.of(0));
        QuizcardDTO card3 = new QuizcardDTO(1, "question", List.of("answer"), List.of());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> restTemplate.exchange("/api/quiz/new", HttpMethod.POST,card1, headers, QuizcardDTO.class);
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> restTemplate.exchange("/api/quiz/new", HttpMethod.POST, card2,headers, QuizcardDTO.class);
        Exception ex3 =assertThrows(IllegalArgumentException.class, () ->  restTemplate.exchange("/api/quiz/new", HttpMethod.POST, card3,headers, QuizcardDTO.class);
        testGetAllReturns204();
    }

}
