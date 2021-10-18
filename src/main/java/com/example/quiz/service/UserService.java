package com.example.quiz.service;

import com.example.quiz.model.DB.User;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.repository.UserRepository;
import com.example.quiz.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    private final UserMapper mapper = new UserMapper();

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public List<UserDTO> getAllUsers() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapUserAndConcealData)
                .toList();
    }

    public List<UserDTO> getAllUsersOnline() {
        return repository
                .findAll()
                .stream()
                .filter(User::isOnline)
                .map(mapper::mapUserAndConcealData)
                .toList();
    }

    public UserDTO getUserById(Integer id) throws NoSuchElementException {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return mapper.mapUserAndConcealData(user.get());
        }
        throw new NoSuchElementException(String.format("No user found with id %d", id));
    }

    public List<UserDTO> getUsersByName(String username) throws NoSuchElementException {
        List<User> users = repository.findByUsername(username);
        if (!users.isEmpty()) {
            return users
                    .stream()
                    .map(mapper::mapUserAndConcealData)
                    .toList();
        }
        throw new NoSuchElementException(String.format("No users found whose name contained %s", username));
    }

    public UserDTO signup(UserDTO user){
        return mapper.mapUserAndConcealData(repository.save(user));
    }

    public UserDTO login(UserDTO user) {
        return user;
    }

}
