package com.example.quiz.service;

import com.example.quiz.model.DB.Highscore;
import com.example.quiz.model.DB.User;
import com.example.quiz.model.DTO.HighscoreDTO;
import com.example.quiz.repository.HighscoreRepository;
import com.example.quiz.service.mapper.HighscoreMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HighscoreServiceTest {

    HighscoreRepository repository = mock(HighscoreRepository.class);

    HighscoreMapper mapper = new HighscoreMapper();

    private final HighscoreService service = new HighscoreService(repository);

    @Test
    void getHighscores() {
        Highscore highscore = buildHighscore();
        when(repository.findAll()).thenReturn(List.of(highscore));
        List<HighscoreDTO> actual = service.getHighscores();
        assertIterableEquals(List.of(mapper.mapHighscoreToDTO(highscore)), actual);
        verify(repository).findAll();
    }

    @Test
    void saveHighscore() {
        Highscore highscore = buildHighscore();
        when(repository.save(highscore)).thenReturn(highscore);
        HighscoreDTO actual = service.saveHighscore(mapper.mapHighscoreToDTO(highscore));
        assertThat(actual).isEqualTo(mapper.mapHighscoreToDTO(highscore));
        verify(repository).save(highscore);
    }

    private Highscore buildHighscore() {
        return Highscore.builder()
                .id(1)
                .user(User.builder()
                        .id(1)
                        .username("user1")
                        .email("user@email.com")
                        .password("123")
                        .isOnline(false)
                        .build())
                .score(10)
                .build();

    }
}
