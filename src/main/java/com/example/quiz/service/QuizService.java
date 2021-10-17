package com.example.quiz.service;

import com.example.quiz.model.Quizcard;
import com.example.quiz.model.QuizcardDTO;
import com.example.quiz.repository.QuizRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
public class QuizService {

    @Autowired
    private final QuizRepository repository;

    private QuizcardMapper mapper = new QuizcardMapper();

    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }


    public List<Quizcard> getAllCards() throws NoSuchElementException {
        List <Quizcard> cards =repository.findAll();
        if(cards.isEmpty()) throw new NoSuchElementException("No quizcards created yet");
        return cards;
    }

    public Quizcard createQuizcard(QuizcardDTO quizcard) throws IllegalArgumentException {
        Quizcard persistentQuizcard = mapper.mapQuizcard(quizcard);
        validateQuizcardBody(persistentQuizcard);
        return repository.save(persistentQuizcard);

    }
    private void validateQuizcardBody(Quizcard quizcard) throws IllegalArgumentException{
        if(quizcard.getQuestion().length() == 0) throw new IllegalArgumentException("Question can't be empty");
        if(quizcard.getChoices().isEmpty()) throw new IllegalArgumentException("Choices can't be empty");
        if(quizcard.getAnswerIndices().isEmpty()) throw new IllegalArgumentException("Correct answer can't be empty");
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

    public boolean validateQuizcardAnswer(QuizcardDTO answerGiven) throws IllegalArgumentException, NoSuchElementException{
        Quizcard persistentQuizcard = mapper.mapQuizcard(answerGiven);
        Quizcard originalQuizcard = findQuizcardById(persistentQuizcard.getId());
        Integer largestAnswerIndex = Collections.max(persistentQuizcard.getAnswerIndices());
        if(originalQuizcard.getChoices().size() <= largestAnswerIndex) {
            throw new IllegalArgumentException("Answer with this index doesn't exist");
        }
        List<Integer> originalAnswers = originalQuizcard.getAnswerIndices();
        List<Integer> answersGiven = persistentQuizcard.getAnswerIndices();
       return originalAnswers
               .stream()
               .filter(answersGiven::contains)
               .toList().size() == originalAnswers.size();
    }
}
