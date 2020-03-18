package com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question;

public class QuestionNotFound extends RuntimeException {

    public QuestionNotFound (String message) {
        super(message);
    }
}
