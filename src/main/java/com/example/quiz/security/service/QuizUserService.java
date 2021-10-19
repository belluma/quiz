package com.example.quiz.security.service;

import com.example.quiz.security.model.QuizUser;
import com.example.quiz.security.model.UserDTO;
import com.example.quiz.security.repository.QuizUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuizUserService implements UserDetailsService {

    @Autowired
    private final QuizUserRepository repository;

    private final UserMapper mapper = new UserMapper();

    public QuizUserService(QuizUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<QuizUser> quizUser =repository.findById(username);
        if(quizUser.isEmpty()) throw new UsernameNotFoundException("dfsakl");
        return User.withUsername(username).password(quizUser.get().getPassword()).authorities("user").build();


//        return repository.findByUsername(username)
//                .map(quizUser -> User
//                        .withUsername("123")
//                        .password(quizUser.getPassword())
//                        .authorities("user").build()).get()
//                .orElseThrow(new UsernameNotFoundException("fdsa'"));
    }



//    public List<UserDTO> getAllUsers() {
//        return repository
//                .findAll()
//                .stream()
//                .map(mapper::mapUserAndConcealData)
//                .toList();
//    }
//
//    public List<UserDTO> getAllUsersOnline() {
//        return repository
//                .findAll()
//                .stream()
//                .filter(QuizUser::isOnline)
//                .map(mapper::mapUserAndConcealData)
//                .toList();
//    }
//
//    public UserDTO getUserById(Integer id) throws NoSuchElementException {
//        Optional<QuizUser> user = repository.findById(id);
//        if (user.isPresent()) {
//            return mapper.mapUserAndConcealData(user.get());
//        }
//        throw new NoSuchElementException(String.format("No user found with id %d", id));
//    }
//
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
//
//    public UserDTO login(UserDTO user) {
//        return user;
//    }

}
