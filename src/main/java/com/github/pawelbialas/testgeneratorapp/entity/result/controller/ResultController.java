package com.github.pawelbialas.testgeneratorapp.entity.result.controller;

import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

    private final ResultServiceImpl resultService;

    public ResultController(ResultServiceImpl resultService) {
        this.resultService = resultService;
    }
}
