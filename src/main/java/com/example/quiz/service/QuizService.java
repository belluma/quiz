package com.example.quiz.service;

import com.example.quiz.model.Quizcard;
import com.example.quiz.repository.QuizRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
public class QuizService {

    @Autowired
    private final QuizRepository repository;

    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }


    public List<Quizcard> getAllCards() throws NoSuchElementException {
        List <Quizcard> cards =repository.findAll();
        if(cards.size() == 0) throw new NoSuchElementException("No quizcards created yet");
        return cards;
    }

    public Quizcard createQuizcard(Quizcard quizcard) throws IllegalArgumentException {
        handleCreateQuizcardErrors(quizcard);
        return repository.save(quizcard);

    }
    private void handleCreateQuizcardErrors(Quizcard quizcard) throws IllegalArgumentException{
        if(quizcard.getQuestion().length() == 0) throw new IllegalArgumentException("Question can't be empty");
        if(quizcard.getChoices().size() == 0) throw new IllegalArgumentException("Choices can't be empty");
        if(quizcard.getAnswerIndices().size() == 0) throw new IllegalArgumentException("Correct answer can't be empty");
        if(quizcard.getChoices().size() <= Collections.max(quizcard.getAnswerIndices()))
            throw new IllegalArgumentException("Index of correct answer out of bounds");
        if(quizcard.getChoices().size() < quizcard.getAnswerIndices().size())
            throw new IllegalArgumentException("More correct answers than choices given");
        if(Collections.min(quizcard.getAnswerIndices()) < 0)
            throw new IllegalArgumentException("Index of correct answer can't be negative");
    }

}
