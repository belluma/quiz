package com.example.quiz.controller;

import com.example.quiz.model.Quizcard;
import com.example.quiz.service.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void testGetAllCards() throws Exception {
        when(quizService.getAllCards()).thenReturn(quizcards);
        this.mockMvc.perform(get("/api/quiz"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).getAllCards();
    }

    @Test
    public void testGetAllCardsReturns204WhenNoCards() throws Exception {
        when(quizService.getAllCards()).thenThrow(new NoSuchElementException("No content"));
        this.mockMvc.perform(get("/api/quiz"))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).getAllCards();
    }

    @Test
    public void createNewCard() throws  Exception{
        when(quizService.createQuizcard(quizcards.get(0))).thenReturn(quizcards.get(0));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(quizcards.get(0));
        } catch (JsonProcessingException e) {
            fail();
        }
        this.mockMvc.perform(post("/api/quiz")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).createQuizcard(quizcards.get(0));
    }
    @Test
    void createNewCardReturnsErrorOnIllegalInput() throws Exception{
        when(quizService.createQuizcard(quizcards.get(0)))
                .thenThrow(new IllegalArgumentException());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(quizcards.get(0));
        } catch (JsonProcessingException e) {
            fail();
        }
        this.mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).createQuizcard(quizcards.get(0));
    }
}
