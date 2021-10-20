package com.example.quiz.service;

import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.repository.QuizUserRepository;
import com.example.quiz.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final QuizUserRepository repository;

    private final UserMapper mapper = new UserMapper();

    public UserDTO getUserByUsername(String username){
        return mapper.mapUserAndConcealData(repository.findByUsername(username).orElseThrow());
    }

//    public List<UserDTO> getAllUsers() {
//        return repository
//                .findAll()
//                .stream()
//                .map(mapper::mapUserAndConcealData)
//                .toList();
//    }

//    public List<UserDTO> getAllUsersOnline() {
//        return repository
//                .findAll()
//                .stream()
//                .filter(QuizUser::isOnline)
//                .map(mapper::mapUserAndConcealData)
//                .toList();
//    }

//    public UserDTO getUserById(Integer id) throws NoSuchElementException {
//        Optional<QuizUser> user = repository.findById(id);
//        if (user.isPresent()) {
//            return mapper.mapUserAndConcealData(user.get());
//        }
//        throw new NoSuchElementException(String.format("No user found with id %d", id));
//    }

//    public List<UserDTO> getUsersByName(String username) throws NoSuchElementException {
//        List<QuizUser> quizUsers = repository.findByUsername(username);
//        if (!quizUsers.isEmpty()) {
//            return quizUsers
//                    .stream()
//                    .map(mapper::mapUserAndConcealData)
//                    .toList();
//        }
//        throw new NoSuchElementException(String.format("No users found whose name contained %s", username));
//    }
//
//    public UserDTO signup(UserDTO user){
//        return mapper.mapUserAndConcealData(repository.save(mapper.mapUser(user)));
//    }

//    public UserDTO login(UserDTO user) {
//        return user;
//    }

}
