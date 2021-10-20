package com.example.quiz.service;

import com.example.quiz.security.model.QuizUser;
import com.example.quiz.security.model.UserDTO;
import com.example.quiz.security.repository.QuizUserRepository;
import com.example.quiz.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@Service
@RequiredArgsConstructor
class QuizUserServiceTest {

    @Autowired
    QuizUserRepository repository = mock(QuizUserRepository.class);

    UserMapper mapper = new UserMapper();

    private final UserService service = new UserService(repository);

//    @Test
//    void getAllUsers() {
//        QuizUser quizUser = buildUser(1);
//        when(repository.findAll()).thenReturn(List.of(quizUser));
//        List<UserDTO> actual = service.getAllUsers();
//        assertIterableEquals(List.of(mapper.mapUserAndConcealData(quizUser)), actual);
//        verify(repository).findAll();
//    }
//
//    @Test
//    void getAllUsersOnline() {
//        QuizUser quizUser = buildUser(1);
//        QuizUser quizUser2 = buildUser(2);
//        quizUser2.setOnline(true);
//        when(repository.findAll()).thenReturn(List.of(quizUser, quizUser2));
//        List<UserDTO> actual = service.getAllUsersOnline();
//        assertIterableEquals(List.of(mapper.mapUserAndConcealData(quizUser2)), actual);
//        verify(repository).findAll();
//    }
//
//    @Test
//    void getUserById() {
//        QuizUser quizUser = buildUser(1);
//        when(repository.findById(1)).thenReturn(Optional.of(quizUser));
//        UserDTO actual = service.getUserById(1);
//        assertThat(actual).isEqualTo(mapper.mapUserAndConcealData(quizUser));
//        verify(repository).findById(1);
//    }
//
//    @Test
//    void getUserByName() {
//        QuizUser quizUser = buildUser(1);
//        QuizUser quizUser2 = buildUser(2);
//        when(repository.findByUsername("user")).thenReturn(List.of(quizUser, quizUser2));
//        List<UserDTO> expected = List.of(mapper.mapUserAndConcealData(quizUser), mapper.mapUserAndConcealData(quizUser2));
//        List<UserDTO> actual = service.getUsersByName("user");
//        assertIterableEquals(actual, expected);
//        verify(repository).findByUsername("user");
//    }
//
//    @Test
//    void signup() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    private QuizUser buildUser(Integer id) {
//        return QuizUser.builder()
//                .username("John")
//                .email("user@email.com")
//                .password("password")
//                .isOnline(false)
//                .build();
//    }

}
