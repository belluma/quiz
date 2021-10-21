package com.example.quiz.controller;

import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.service.mapper.QuizcardMapper;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    @Order(1)
    void test() {
        assertTrue(container.isRunning());
    }

    @Test
    @Order(2)
    void testGetAllReturns204() {
        Exception ex = assertThrows(NoSuchElementException.class, () -> quizController.getAllCards());
        assertThat(ex.getMessage().equals("No quizcards created yet"));
    }

    @Test
    @Order(3)
    void testCreateNewCard() {
//        QuizcardDTO response = quizController.createNewCard(card);
//        ResponseEntity<QuizcardDTO> response = restTemplate.postForEntity("/api/quz/new", card, QuizcardDTO.class);
//        ResponseEntity<QuizcardDTO> response = restTemplate.exchange("/api/quz/new", HttpMethod.POST, new HttpEntity<>(card, headers), QuizcardDTO.class);
//        assertThat(response.getBody()).isEqualTo(cardWithConcealedAnswers);
    }
    @Test
    @Order(4)
    void testGetAllCardReturnsListOfOneCard() {
        List<QuizcardDTO> response = quizController.getAllCards();
        assertIterableEquals(response, List.of(cardWithConcealedAnswers));

    }
    @Test
    @Order(5)
    void createNewCardReturns406OnWrongInput() {
        QuizcardDTO card1 = new QuizcardDTO(1, "", List.of("answer"), List.of(0));
        QuizcardDTO card2 = new QuizcardDTO(1, "question", List.of(), List.of(0));
        QuizcardDTO card3 = new QuizcardDTO(1, "question", List.of("answer"), List.of());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> quizController.createNewCard(card1));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> quizController.createNewCard(card2));
        Exception ex3 =assertThrows(IllegalArgumentException.class, () ->  quizController.createNewCard(card3));
    }

    @Test
    void testNoCardHasBeenAdded() {
        testGetAllCardReturnsListOfOneCard();
    }
}
