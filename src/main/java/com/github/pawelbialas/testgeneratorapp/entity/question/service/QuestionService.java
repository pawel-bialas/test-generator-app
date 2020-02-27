package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import org.jetbrains.annotations.NotNull;

public interface QuestionService {

    Question saveOrUpdate(@NotNull QuestionDto questionDto);
}
