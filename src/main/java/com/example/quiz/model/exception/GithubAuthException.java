package com.example.quiz.model.exception;

import java.util.NoSuchElementException;

public class GithubAuthException extends RuntimeException {

    public GithubAuthException() {
        super();
    }
    public GithubAuthException(String s, Throwable cause) {
        super(s, cause);
    }
    public GithubAuthException(Throwable cause) {
        super(cause);
    }
    public GithubAuthException(String s) {
        super(s);
    }
}
