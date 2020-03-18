package com.github.pawelbialas.testgeneratorapp.shared.domain.exception.result;

public class ResultBadRequest extends RuntimeException {

    public ResultBadRequest(String message) {
        super(message);
    }
}
