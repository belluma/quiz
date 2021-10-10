package com.example.quiz.controller;

import com.example.quiz.model.Quizcard;
import com.example.quiz.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest
@RunWith(MockitoJUnitRunner.class)
public class QuizControllerUnitTest {

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Quizcard> quizcardCaptor = ArgumentCaptor.forClass(Quizcard.class);

    @Autowired
    protected WebApplicationContext wac;

    @InjectMocks
    QuizController quizController;

    @Mock
    QuizService quizService;

    private List<Quizcard> quizcards;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.quizController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        Quizcard todo1 = new Quizcard(0, "question", List.of("right answer", "wrong answer"), List.of(0));
        Quizcard todo2 = new Quizcard(1, "question2", List.of("wrong answer", "right answer"), List.of(1));
        quizcards = new ArrayList<>();
        quizcards.add(todo1);
        quizcards.add(todo2);
    }

    @Test
    public void testGetAllCards() throws Exception{
        when(quizService.getAllCards()).thenReturn(quizcards);
        this.mockMvc.perform(get("/api/quiz"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).getAllCards();
    }

}
