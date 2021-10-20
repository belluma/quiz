package com.example.quiz.security.service;
import com.example.quiz.model.DTO.UserDTO;
import com.example.quiz.security.repository.QuizUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private final QuizUserRepository repository;

    public UserAuthService(QuizUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findById(username)
                .map(appUser -> User
                        .withUsername(username)
                        .password(appUser.getPassword())
                        .authorities("user")
                        .build())
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist: " + username));
    }

    public UserDTO signup(UserDTO user) {
        AppUser appUser = mapper
    }
}
