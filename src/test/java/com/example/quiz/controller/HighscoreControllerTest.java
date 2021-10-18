package com.example.quiz.controller;

import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import com.example.quiz.model.DTO.HighscoreDTO;
import com.example.quiz.service.mapper.HighscoreMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HighscoreControllerTest {

    @Autowired
    HighscoreController highscoreController;
    @Autowired
    GlobalExceptionHandler exceptionHandler;

    HighscoreMapper mapper = new HighscoreMapper();

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withDatabaseName("quiz")
            .withUsername("quiz")
            .withPassword("quiz");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    HighscoreDTO highscore = HighscoreDTO.builder()
            .id(1)
            .score(10)
            .build();

    @Test
    @Order(1)
    void getHighscores() {
        List<HighscoreDTO> response = highscoreController.getHighscores();
        assertTrue(response.isEmpty());
    }

    @Test
    @Order(2)
    void saveHighscore() {
        HighscoreDTO response = highscoreController.saveHighscore(highscore);
        assertThat(response).isEqualTo(highscore);
    }

    @Test
    @Order(3)
    void checkHighscoreHasBeenAddedToDb() {
        List<HighscoreDTO> response = highscoreController.getHighscores();
        assertThat(response.get(0)).isEqualTo(highscore);

    }
}
