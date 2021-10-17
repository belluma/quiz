package com.example.quiz.service;

import com.example.quiz.model.DB.Quizcard;
import com.example.quiz.model.DTO.QuizcardDTO;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.service.mapper.QuizcardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizServiceTest {

    QuizRepository repository = mock(QuizRepository.class);

    QuizcardMapper mapper = new QuizcardMapper();

    private final QuizService service = new QuizService(repository);

    @Test
    void getAllQuizcards() {
        when(repository.findAll()).thenReturn(List.of(new Quizcard()));
        List<QuizcardDTO> actual = service.getAllCards();
        assertIterableEquals(List.of(mapper.mapQuizcardToDTOWithoutCorrectAnswers(new Quizcard())), actual);
        verify(repository).findAll();
    }

    @Test
    void getAllQuizcardsThrowsWhenEmpty() {
        when(repository.findAll()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, service::getAllCards);
        verify(repository).findAll();
    }

    @Test
    void createQuizcard() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(0));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        when(repository.save(persistentCard)).thenReturn(persistentCard);
        QuizcardDTO actual = service.createQuizcard(card);
        assertThat(actual).isEqualTo(mapper.mapQuizcardToDTOWithoutCorrectAnswers(persistentCard));
        verify(repository).save(persistentCard);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateQuizcardThrows")
    void createQuizcardThrows(QuizcardDTO illegalQuizcard, String errorMessage ){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(illegalQuizcard));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }
private static Stream<Arguments> provideArgumentsForCreateQuizcardThrows() {
    return Stream.of(
            Arguments.of(new QuizcardDTO(1, "", List.of("a", "b", "c"), List.of(0)), "Question can't be empty"),
            Arguments.of(new QuizcardDTO(1, "question", List.of(), List.of(0)), "Choices can't be empty"),
            Arguments.of(new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of()), "Correct answer can't be empty"),
            Arguments.of(new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(3)), "Index of correct answer out of bounds"),
            Arguments.of(new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(0,0,1,1)), "More correct answers than choices given"),
            Arguments.of(new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(-1)), "Index of correct answer can't be negative")
    );
}
    @Test
    void createQuizcardThrowsWhenNoChoicesGiven() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of(), List.of(0));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(persistentCard);
        assertEquals("Choices can't be empty", exception.getMessage());
    }

    @Test
    void createQuizcardThrowsWhenNoCorrectAnswerGiven() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of());
        Quizcard persistentCard = mapper.mapQuizcard(card);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(persistentCard);
        assertEquals("Correct answer can't be empty", exception.getMessage());
    }

    @Test
    void createQuizcardThrowsWhenCorrectAnswerIndexHigherThanChoicesLength() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(3));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(persistentCard);
        assertEquals("Index of correct answer out of bounds", exception.getMessage());
    }

    @Test
    void createQuizcardThrowsWhenMoreCorrectAnswersThanChoices() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(0, 1, 1, 2));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(persistentCard);
        assertEquals("More correct answers than choices given", exception.getMessage());
    }

    @Test
    void createQuizcardThrowsWhenCorrectAnswerIndexNegative() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(-1));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createQuizcard(card));
        verify(repository, never()).save(persistentCard);
        assertEquals("Index of correct answer can't be negative", exception.getMessage());
    }

    @Test
    void findQuizcardById() {
        QuizcardDTO card = new QuizcardDTO(1, "question", List.of("a", "b", "c"), List.of(0));
        Quizcard persistentCard = mapper.mapQuizcard(card);
        when(repository.findById(card.getId())).thenReturn(Optional.of(persistentCard));
        Quizcard actual = service.findQuizcardById(1);
        verify(repository).findById(1);
        assertThat(actual).isEqualTo(persistentCard);
    }

    @Test
    void findQuizcardByIdThrowsWhenNotFound() {
        when(repository.findById(123)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> service.findQuizcardById(123));
        verify(repository).findById(123);
        assertThat(exception.getMessage()).isEqualTo("Couldn't find a question with id 123");

    }

    @Test
    void validateQuizcardAnswerWithCorrectAnswer() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0));
        QuizcardDTO answerGiven = new QuizcardDTO(1, "", List.of(), List.of(0));
        when(repository.findById(card.getId())).thenReturn(Optional.of(card));
        boolean actual = service.validateQuizcardAnswer(answerGiven);
        verify(repository).findById(1);
        assertTrue(actual);
    }

    @Test
    void validateQuizcardAnswerWithMutlipleCorrectAnswers() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0,1));
        QuizcardDTO answerGiven = new QuizcardDTO(1, "", List.of(), List.of(0, 1));
        when(repository.findById(1)).thenReturn(Optional.of(card));
        boolean actual = service.validateQuizcardAnswer(answerGiven);
        verify(repository).findById(1);
        assertTrue(actual);
    }

    @Test
    void validateQuizcardAnswerWithFalseAnswer() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0));
        QuizcardDTO answerGiven = new QuizcardDTO(1, "", List.of(), List.of(1));
        when(repository.findById(card.getId())).thenReturn(Optional.of(card));
        boolean actual = service.validateQuizcardAnswer(answerGiven);
        verify(repository).findById(1);
        assertFalse(actual);
    }

    @Test
    void validateQuizcardAnswerWhenOnlyOneOfMultipleCorrectAnswersGiven() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0, 1));
        QuizcardDTO answerGiven = new QuizcardDTO(1, "", List.of(), List.of(0));
        when(repository.findById(card.getId())).thenReturn(Optional.of(card));
        boolean actual = service.validateQuizcardAnswer(answerGiven);
        verify(repository).findById(1);
        assertFalse(actual);
    }

    @Test
    void validateQuizcardAnswerThrowsWhenNotFound() {
        when(repository.findById(123)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoSuchElementException.class, () -> service.findQuizcardById(123));
        verify(repository).findById(123);
        assertThat(exception.getMessage()).isEqualTo("Couldn't find a question with id 123");

    }

    @Test
    void validateQuizcardAnswerThrowsWhenIndexOutOfBounds() {
        Quizcard card = new Quizcard(1, "question", List.of("a", "b", "c"), List.of(0, 1));
        QuizcardDTO answerGiven = new QuizcardDTO(1, "", List.of(), List.of(3));
        when(repository.findById(card.getId())).thenReturn(Optional.of(card));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.validateQuizcardAnswer(answerGiven));
        assertThat(exception.getMessage()).isEqualTo("Answer with this index doesn't exist");
        verify(repository).findById(1);}

}
