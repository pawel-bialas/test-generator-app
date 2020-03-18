package com.github.pawelbialas.testgeneratorapp.entity.question.exception;

public class QuestionBadRequest extends RuntimeException {

    public QuestionBadRequest(String message) {
        super(message);
    }
}
