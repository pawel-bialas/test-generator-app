package com.github.pawelbialas.testgeneratorapp.shared.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {


    private final String errorClassPath = "class com.github.pawelbialas.testgeneratorapp.shared.domain.exception.";
//
//    @ExceptionHandler(value = {Exception.class, InternalServerErrorException.class})
//    public ResponseEntity<Object> handleBaseExceptionHandler(Exception exc, WebRequest request) {
//
//        String errorDescription = exc.getLocalizedMessage();
//        if (errorDescription == null) {
//            errorDescription = exc.toString();
//        }
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
//        return new ResponseEntity<>(
//                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(value = {BadRequestException.class})
//    public ResponseEntity<Object> badRequestExceptionsHandler(Exception exc, WebRequest request) {
//
//        String errorDescription = exc.getLocalizedMessage();
//        if (errorDescription == null) {
//            errorDescription = exc.toString();
//        }
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
//        return new ResponseEntity<>(
//                errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {NotFoundException.class})
//    public ResponseEntity<Object> notFoundExceptionsHandler(Exception exc, WebRequest request) {
//
//        String errorDescription = exc.getLocalizedMessage();
//        if (errorDescription == null) {
//            errorDescription = exc.toString();
//        }
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
//        return new ResponseEntity<>(
//                errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> statusExceptionHandler(Exception exc, WebRequest request) {


        String errorDescription = exc.getLocalizedMessage();
        if (errorDescription == null) {
            errorDescription = exc.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);

        Class<? extends Exception> inputClass = exc.getClass();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        switch (inputClass.toString()) {
            case errorClassPath + "BadRequestException":
                status = HttpStatus.BAD_REQUEST;
                break;
            case errorClassPath + "InternalServerErrorException":
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case errorClassPath + "NotAcceptableException":
                status = HttpStatus.NOT_ACCEPTABLE;
                break;
            case errorClassPath + "NotFoundException":
                status = HttpStatus.NOT_FOUND;
                break;
        }
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), status);
    }

}
