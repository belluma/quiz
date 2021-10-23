package com.example.quiz.model;

import java.util.NoSuchElementException;

public class NoCardsCreatedYetException extends NoSuchElementException {

    public NoCardsCreatedYetException() {
        super();
    }
    public NoCardsCreatedYetException(String s, Throwable cause) {
        super(s, cause);
    }
    public NoCardsCreatedYetException(Throwable cause) {
        super(cause);
    }
    public NoCardsCreatedYetException(String s) {
        super(s);
    }
}
