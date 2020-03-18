package com.github.pawelbialas.testgeneratorapp.shared.domain.exception;

import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.Skilltest.SkillTestServiceBadRequest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.answer.AnswerNotAcceptable;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.contestant.ContestantServiceNotFound;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question.QuestionBadRequest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question.QuestionInternalServerError;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.question.QuestionNotFound;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.result.ResultBadRequest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.Skilltest.SkillTestNotFound;
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

    @ExceptionHandler(value = {Exception.class, QuestionInternalServerError.class})
    public ResponseEntity<Object> handleBaseExceptionHandler(Exception exc, WebRequest request) {

        String errorDescription = exc.getLocalizedMessage();
        if (errorDescription == null) {
            errorDescription = exc.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResultBadRequest.class, SkillTestServiceBadRequest.class, QuestionBadRequest.class})
    public ResponseEntity<Object> badRequestExceptionsHandler(Exception exc, WebRequest request) {

        String errorDescription = exc.getLocalizedMessage();
        if (errorDescription == null) {
            errorDescription = exc.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ContestantServiceNotFound.class, QuestionNotFound.class, SkillTestNotFound.class})
    public ResponseEntity<Object> notFoundExceptionsHandler(Exception exc, WebRequest request) {

        String errorDescription = exc.getLocalizedMessage();
        if (errorDescription == null) {
            errorDescription = exc.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AnswerNotAcceptable.class})
    public ResponseEntity<Object> notAcceptableExceptionsHandler(Exception exc, WebRequest request) {

        String errorDescription = exc.getLocalizedMessage();
        if (errorDescription == null) {
            errorDescription = exc.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

}
