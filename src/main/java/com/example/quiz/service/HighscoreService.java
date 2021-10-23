package com.example.quiz.service;

import com.example.quiz.model.DTO.HighscoreDTO;
import com.example.quiz.repository.HighscoreRepository;
import com.example.quiz.service.mapper.HighscoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HighscoreService {

    private final HighscoreRepository repository;

    private final HighscoreMapper mapper = new HighscoreMapper();

    public List<HighscoreDTO> getHighscores() {

        return repository.findAll()
                .stream()
                .map(mapper::mapHighscoreToDTO)
                .toList();
    }

    public HighscoreDTO saveHighscore(HighscoreDTO highscore) {
        return mapper.mapHighscoreToDTO(repository.save(mapper.mapHighscore(highscore)));
    }
}
