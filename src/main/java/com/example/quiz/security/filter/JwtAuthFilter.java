package com.example.quiz.security.filter;

import com.example.quiz.security.service.JWTUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JWTUtilService jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        String token = getToken(request);
        try {
            if(token != null && !token.isBlank()){
                String username = jwtUtils.extractUserName(token);
                setSecurityContext(username);
            }
        } catch (Exception e) {
            throw new AccessDeniedException("No valid token! Access denied!", e);
        }
    }

    private void setSecurityContext(String username){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, "", List.of());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null){
            return authHeader.replace("Bearer", "").trim();
        }
        return null;
    }

}
