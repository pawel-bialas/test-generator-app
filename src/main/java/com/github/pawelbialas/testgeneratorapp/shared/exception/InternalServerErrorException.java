package com.github.pawelbialas.testgeneratorapp.shared.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }
}
