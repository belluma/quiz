package com.example.quiz.service.mapper;

import com.example.quiz.model.DB.Quizcard;
import com.example.quiz.model.DTO.QuizcardDTO;

public class QuizcardMapper {
    public Quizcard mapQuizcard(QuizcardDTO quizcard) {
        return new Quizcard(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices(), quizcard.getAnswerIndices());
    }

    public QuizcardDTO mapQuizcardToDTO(Quizcard quizcard) {
        return new QuizcardDTO(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices(), quizcard.getAnswerIndices());
    }

    public QuizcardDTO mapQuizcardToDTOWithoutCorrectAnswers(Quizcard quizcard) {
        return new QuizcardDTO(quizcard.getId(), quizcard.getQuestion(), quizcard.getChoices());
    }
}
