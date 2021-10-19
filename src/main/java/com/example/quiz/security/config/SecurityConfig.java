package com.example.quiz.security.config;

import com.example.quiz.security.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthService userAuthService;

    @Autowired
    public SecurityConfig(UserAuthService userAuthService){
        this.userAuthService = userAuthService;
    }

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userAuthService);
    }


    //use nopassencoder to make it easier !!1! important to change for production !!1!
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//to enable posts (and also delete/put???)
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .and().formLogin()
//                .and().httpBasic();
//    }
}
