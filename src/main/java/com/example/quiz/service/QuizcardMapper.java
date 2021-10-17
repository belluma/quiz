package com.example.quiz.service;

import com.example.quiz.model.DB.Quizcard;
import com.example.quiz.model.DTO.QuizcardDTO;

public class QuizcardMapper {
    public Quizcard mapQuizcard(QuizcardDTO quizcard) {
        return new Quizcard(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices(), quizcard.getAnswerIndices());
    }
    public QuizcardDTO mapToDTO(Quizcard quizcard) {
        return new QuizcardDTO(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices(), quizcard.getAnswerIndices());
    }
public QuizcardDTO mapToDTOWithoutCorrectAnswers(Quizcard quizcard) {
        return new QuizcardDTO(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices());
    }
}
