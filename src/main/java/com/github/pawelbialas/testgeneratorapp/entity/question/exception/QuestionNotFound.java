package com.github.pawelbialas.testgeneratorapp.entity.question.exception;

public class QuestionNotFound extends RuntimeException {

    public QuestionNotFound (String message) {
        super(message);
    }
}
