package com.example.quiz.service;

import com.example.quiz.model.Quizcard;
import com.example.quiz.repository.QuizRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizServiceTest {

    @Captor
    ArgumentCaptor<Quizcard> quizCard = ArgumentCaptor.forClass(Quizcard.class);

    QuizRepository repository = mock(QuizRepository.class);

    private final QuizService service = new QuizService(repository);

    @Test
    void getAllQuizcards() {
        when(repository.findAll()).thenReturn(List.of(new Quizcard()));
        List<Quizcard> actual = service.getAllCards();
        assertIterableEquals(List.of(new Quizcard()), actual);
        verify(repository).findAll();
    }

    @Test
    void getAllQuizcardsThrowsWhenEmpty() {
        when(repository.findAll()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, () -> service.getAllCards());
        verify(repository).findAll();
    }

    @Test
    void createQuizcard() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0));
        when(repository.save(card)).thenReturn(card);
        Quizcard actual = service.createQuizcard(card);
        assertThat(actual).isEqualTo(card);
        verify(repository).save(card);
    }

    @Test
    void createQuizcardThrowsWhenNoQuestionGiven() {
        Quizcard card = new Quizcard(1, "", List.of("a", "b", "c"), List.of(0));
        Quizcard card2 = new Quizcard(1, "question", List.of(), List.of(0));
        Quizcard card3 = new Quizcard(1, "question", List.of("a", "b", "c"), List.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card2));
        assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card3));
        verify(repository, never()).save(card);
        assertEquals("Question can't be empty", exception.getMessage());
    }
    @Test
    void createQuizcardThrowsWhenNoChoicesGiven() {
        Quizcard card = new Quizcard(1, "question", List.of(), List.of(0));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(card);
        assertEquals("Choices can't be empty", exception.getMessage());
    }
    @Test
    void createQuizcardThrowsWhenNoCorrectAnswerGiven() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(card);
        assertEquals("Correct answer can't be empty", exception.getMessage());
    }
    @Test
    void createQuizcardThrowsWhenCorrectAnswerIndexHigherThanChoicesLength() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(3));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(card);
        assertEquals("Index of correct answer out of bounds", exception.getMessage());
    }
    @Test
    void createQuizcardThrowsWhenMoreCorrectAnswersThanChoices() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0, 1, 1, 2));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(card);
        assertEquals("More correct answers than choices given", exception.getMessage());
    }
    @Test
    void createQuizcardThrowsWhenCorrectAnswerIndexNegative() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(-1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(card);
        assertEquals("Index of correct answer can't be negative", exception.getMessage());
    }

}
