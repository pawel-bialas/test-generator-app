package com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question;

public class QuestionInternalServerError extends RuntimeException {

    public QuestionInternalServerError (String message) {
        super(message);
    }
}
