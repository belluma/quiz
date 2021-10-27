package com.example.quiz.security.service;

import com.example.quiz.model.DTO.GithubAccessTokenDTO;
import com.example.quiz.model.DTO.GithubUserDTO;
import com.example.quiz.model.GithubRequestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@TestPropertySource(properties={"github.client.id=client_id", "github.client.secret=client_secret"})
//@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
class GithubAuthServiceTest {

    private final UserAuthUtils utils = new UserAuthUtils();
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final GithubAuthService mockService = mock(GithubAuthService.class);

    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_URL = "https://api.github.com/user";
    private final GithubAuthService service = new GithubAuthService(utils, restTemplate);


    @Test
    void testGetTokenFromGithub() {
        GithubRequestData requestData = new GithubRequestData("client_id", "github_secret", "1234");
        HttpHeaders headers = utils.jsonHeaders();
        GithubAccessTokenDTO token = new GithubAccessTokenDTO("1234");
//        doReturn(token).when(restTemplate).exchange(GITHUB_TOKEN_URL,
//                HttpMethod.POST,
//                new HttpEntity<>(requestData, headers),
//                GithubAccessTokenDTO.class);
//        String actual = service.getTokenFromGithub("1234");
        //        service.getTokenFromGithub("1234");
    }

    @Test
    void getTokenFromGithubThrowsWhenBodyNull() {

    }

    @Test
    void testGetUsernameFromGithub() {
        GithubRequestData requestData = new GithubRequestData("client_id", "github_secret", "1234");
        HttpHeaders headers = utils.jsonHeaders();
        GithubAccessTokenDTO token = new GithubAccessTokenDTO("1234");
        ResponseEntity<GithubAccessTokenDTO> tokenResponse = ResponseEntity.ok(token);
                //new ResponseEntity<GithubAccessTokenDTO>(token, HttpStatus.OK);
//        doReturn("1234").when(mockService).getTokenFromGithub("1234");
        doReturn(tokenResponse).when(restTemplate).exchange(GITHUB_TOKEN_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestData, headers),
                GithubAccessTokenDTO.class);
        HttpHeaders authHeaders = utils.authHeaders("1234");
        GithubUserDTO user = new GithubUserDTO("username");
        ResponseEntity<GithubUserDTO> usernameResponse = new ResponseEntity<GithubUserDTO>(user, HttpStatus.OK);

        doReturn(usernameResponse).when(restTemplate).exchange(GITHUB_USER_URL,
                HttpMethod.GET,
                new HttpEntity<>(authHeaders),
                GithubAccessTokenDTO.class);
        String actual = service.getUsernameFromGithub("1234");
        verify(restTemplate).exchange(GITHUB_TOKEN_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestData, headers),
                GithubAccessTokenDTO.class);
        assertThat(actual, is("username"));
        //    System.err.println(username);
    }

    @Test
    void getUsernameFromGithubThrowsWhenBodyNull() {

    }

}
