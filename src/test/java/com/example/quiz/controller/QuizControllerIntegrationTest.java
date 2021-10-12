package com.example.quiz.controller;

import com.example.quiz.model.Quizcard;
import com.example.quiz.repository.QuizRepository;
import org.apache.tomcat.jni.Global;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
public class QuizControllerIntegrationTest {

    @Autowired
    QuizController quizController; //= new QuizController();
    @Autowired
    GlobalExceptionHandler exceptionHandler;

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
    Quizcard card = new Quizcard(1, "question", List.of("answer"), List.of(0));
    @Test
    void test() {
        assertTrue(container.isRunning());
    }

//    @Test
//    void testGetAllReturns204() {
//        ResponseEntity<Quizcard> response = quizController.getAllCards();
//        assertThat(response.getStatusCodeValue() == 204);
//    }

//    @Test
//    void testCreateNewCard() {
//        ResponseEntity<Quizcard> response = quizController.createNewCard(card);
//        assertThat(response.getStatusCodeValue() == 200);
//        assertThat(response.getBody()).isEqualTo(card);
//    }
//    @Test
//    void testGetAllCardReturnsListOfOneCard() {
//        ResponseEntity<Quizcard> response = quizController.getAllCards();
//        assertThat(response.getStatusCodeValue() == 200);
//        assertThat(response.getBody()).isEqualTo(List.of(card));
//
//    }
//    @Test
//    void createNewCardReturns406OnWrongInput() {
//        Quizcard card1 = new Quizcard(1, "", List.of("answer"), List.of(0));
//        Quizcard card2 = new Quizcard(1, "question", List.of(), List.of(0));
//        Quizcard card3 = new Quizcard(1, "question", List.of("answer"), List.of());
//        ResponseEntity<Quizcard> response1 = quizController.createNewCard(card1);
//        ResponseEntity<Quizcard> response2 = quizController.createNewCard(card2);
//        ResponseEntity<Quizcard> response3 = quizController.createNewCard(card3);
//        assertThat(response1.getStatusCodeValue() == 406);
//        assertThat(response2.getStatusCodeValue() == 406);
//        assertThat(response3.getStatusCodeValue() == 406);
//    }
//    @Test
//    void testNoCardHasBeenAdded() {
//        testGetAllCardReturnsListOfOneCard();
//    }
}
