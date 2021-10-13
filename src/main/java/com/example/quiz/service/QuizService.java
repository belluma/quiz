package com.example.quiz.service;

import com.example.quiz.model.Quizcard;
import com.example.quiz.repository.QuizRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

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
        validateQuizcardBody(quizcard);
        return repository.save(quizcard);

    }
    private void validateQuizcardBody(Quizcard quizcard) throws IllegalArgumentException{
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

    public Quizcard findQuizcardById(Integer id) throws NoSuchElementException{
        Optional<Quizcard> quizcard = repository.findById(id);
        if(quizcard.isPresent()) return quizcard.get();
        throw new NoSuchElementException(String.format("Couldn't find a question with id %s", id));
    }
    public boolean validateQuizcardAnswer(Quizcard answerGiven) throws IllegalArgumentException, NoSuchElementException{
        Quizcard originalQuizcard = findQuizcardById(answerGiven.getId());
        Integer largestAnswerIndex = Collections.max(answerGiven.getAnswerIndices());
        if(originalQuizcard.getChoices().size() <= largestAnswerIndex) {
            throw new IllegalArgumentException("Answer with this index doesn't exist");
        }
        List<Integer> originalAnswers = originalQuizcard.getAnswerIndices();
        List<Integer> answersGiven = answerGiven.getAnswerIndices();
       return originalAnswers
               .stream()
               .filter(index -> answersGiven.contains(index))
               .toList().size() == originalAnswers.size();
    }
}
