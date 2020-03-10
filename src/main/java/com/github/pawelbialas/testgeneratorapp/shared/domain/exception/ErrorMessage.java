package com.github.pawelbialas.testgeneratorapp.shared.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {

    private Date timestamp;
    private String errorMessage;

}
