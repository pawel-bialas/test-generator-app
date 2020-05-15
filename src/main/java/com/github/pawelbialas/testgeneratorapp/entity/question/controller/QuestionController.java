package com.github.pawelbialas.testgeneratorapp.entity.question.controller;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.shared.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {

    private final QuestionServiceImpl questionServiceImpl;

    public QuestionController(QuestionServiceImpl questionServiceImpl) {
        this.questionServiceImpl = questionServiceImpl;
    }

    @PostMapping(path = "/manage/question/new")
    @ResponseStatus(HttpStatus.CREATED)
    private void addNewQuestion(@RequestBody QuestionDto question) {
        questionServiceImpl.saveOrUpdate(question);
    }

    @PutMapping(path = "/manage/question/update")
    @ResponseStatus(HttpStatus.OK)
    private void updateQuestion (@RequestBody QuestionDto questionDto) {
        questionServiceImpl.findById(questionDto.getId())
                .ifPresentOrElse(questionServiceImpl::saveOrUpdate,
                        () -> {
                    throw new NotFoundException("Question not found");
                });
    }
}
