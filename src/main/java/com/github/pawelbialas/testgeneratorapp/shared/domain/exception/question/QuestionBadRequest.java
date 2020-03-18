package com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question;

public class QuestionBadRequest extends RuntimeException {

    public QuestionBadRequest(String message) {
        super(message);
    }
}
