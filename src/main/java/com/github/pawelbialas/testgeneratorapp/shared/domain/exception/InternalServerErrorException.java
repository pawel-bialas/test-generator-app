package com.github.pawelbialas.testgeneratorapp.entity.question.exception;

public class QuestionInternalServerError extends RuntimeException {

    public QuestionInternalServerError (String message) {
        super(message);
    }
}
