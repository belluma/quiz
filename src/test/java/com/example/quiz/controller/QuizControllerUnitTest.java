package com.example.quiz.controller;

import com.example.quiz.model.Quizcard;
import com.example.quiz.model.QuizcardDTO;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.QuizcardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Autowired
    protected WebApplicationContext wac;

    @InjectMocks
    QuizController quizController;

    @Mock
    QuizService quizService;

    QuizcardMapper mapper = new QuizcardMapper();

    private List<Quizcard> quizcards;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.quizController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        Quizcard quizcard1 = new Quizcard(0, "question", List.of("right answer", "wrong answer"), List.of(0));
        Quizcard quizcard2 = new Quizcard(1, "question2", List.of("wrong answer", "right answer"), List.of(1));
        quizcards = new ArrayList<>();
        quizcards.add(quizcard1);
        quizcards.add(quizcard2);
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
        when(quizService.getAllCards()).thenThrow(new NoSuchElementException("No quizcards created yet"));
        this.mockMvc.perform(get("/api/quiz"))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).getAllCards();
    }

    @Test
    public void createNewCard() throws Exception {
        QuizcardDTO quizcardDTO = mapper.mapToDTO(quizcards.get(0));
        when(quizService.createQuizcard(quizcardDTO)).thenReturn(quizcards.get(0));
        String json = createJsonBodyOfQuizcard1();
        this.mockMvc.perform(post("/api/quiz/new")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).createQuizcard(quizcardDTO);
    }

    @Test
    public void createNewCardThrowsOnIllegalInput() throws Exception {
        QuizcardDTO brokenCard = mapper.mapToDTO(quizcards.get(0));
        when(quizService.createQuizcard(brokenCard))
                .thenThrow(new IllegalArgumentException());
        String json = createJsonBodyOfQuizcard1();
        this.mockMvc.perform(post("/api/quiz/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).createQuizcard(brokenCard);
    }

    @Test
    public void validateAnswer() throws Exception {
        QuizcardDTO card = mapper.mapToDTO(quizcards.get(0));
        String json = createJsonBodyOfQuizcard1();
        when(quizService.validateQuizcardAnswer(card)).thenReturn(true);
        this.mockMvc.perform(post("/api/quiz")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).validateQuizcardAnswer(card);
    }

    @Test
    public void validateAnswerThrowsOnIllegalInput() throws Exception{
        QuizcardDTO brokenCard = mapper.mapToDTO(quizcards.get(0));
        when(quizService.validateQuizcardAnswer(brokenCard))
                .thenThrow(new IllegalArgumentException());
        String json = createJsonBodyOfQuizcard1();
        this.mockMvc.perform(post("/api/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService).validateQuizcardAnswer(brokenCard);
    }
    private String createJsonBodyOfQuizcard1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mapper.mapToDTO(quizcards.get(0)));
    }
}
