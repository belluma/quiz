//package com.example.quiz.controller;
//
//import com.example.quiz.security.service.UserMapper;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class QuizUserControllerTest {
//
//
//    @Autowired
//    UserController userController;
//    @Autowired
//    GlobalExceptionHandler exceptionHandler;
//
//    UserMapper mapper = new UserMapper();
//
//    @Container
//    public static PostgreSQLContainer container = new PostgreSQLContainer()
//            .withDatabaseName("quiz")
//            .withUsername("quiz")
//            .withPassword("quiz");
//
//    @DynamicPropertySource //https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
//    static void postgresqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", container::getJdbcUrl);
//        registry.add("spring.datasource.password", container::getPassword);
//        registry.add("spring.datasource.username", container::getUsername);
//    }
//    @Test
//    void getAllUsers() {
//    }
//
//    @Test
//    void getAllUsersOnline() {
//    }
//
//    @Test
//    void getUserById() {
//    }
//
//    @Test
//    void getUsersByName() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void signup() {
//    }
//}
