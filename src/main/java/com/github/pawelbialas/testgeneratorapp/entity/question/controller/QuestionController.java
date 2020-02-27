package com.github.pawelbialas.testgeneratorapp.entity.question.controller;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final QuestionServiceImpl questionServiceImpl;

    public QuestionController(QuestionServiceImpl questionServiceImpl) {
        this.questionServiceImpl = questionServiceImpl;
    }

    @PostMapping(path = "/manage/question/new")
    @ResponseStatus(HttpStatus.CREATED)
    private void addNewQuestion(@RequestBody Question question) {

        questionServiceImpl.saveOrUpdate(question);
    }
}
