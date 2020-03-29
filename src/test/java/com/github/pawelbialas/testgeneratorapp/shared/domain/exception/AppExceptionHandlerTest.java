package com.github.pawelbialas.testgeneratorapp.shared.domain.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


class AppExceptionHandlerTest {


    WebRequest request;

    AppExceptionHandler handler = new AppExceptionHandler();

    @Test
    void badRequestStatusExceptionHandlerTest() {

        //Given
        ResponseEntity<Object> responseEntity = handler.statusExceptionHandler(new BadRequestException("test"), request);
        //Then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorMessage.class);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).toString()).contains("test");
    }

    @Test
    public void notFoundStatusExceptionHandlerTest() {
        //Given
        ResponseEntity<Object> responseEntity = handler.statusExceptionHandler(new NotFoundException("test123"), request);
        //Then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorMessage.class);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).toString()).contains("test123");

    }

    @Test
    public void notAcceptableStatusExceptionHandlerTest() {
        //Given
        ResponseEntity<Object> responseEntity = handler.statusExceptionHandler(new NotAcceptableException("test456456"), request);
        //Then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorMessage.class);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).toString()).contains("test456456");

    }
}