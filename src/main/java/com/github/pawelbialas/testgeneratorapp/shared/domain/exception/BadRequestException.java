package com.github.pawelbialas.testgeneratorapp.shared.domain.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
