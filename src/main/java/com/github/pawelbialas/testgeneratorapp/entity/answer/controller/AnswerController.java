package com.github.pawelbialas.testgeneratorapp.entity.answer.controller;

import com.github.pawelbialas.testgeneratorapp.entity.answer.service.AnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    private final AnswerServiceImpl answerService;

    public AnswerController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }
}
