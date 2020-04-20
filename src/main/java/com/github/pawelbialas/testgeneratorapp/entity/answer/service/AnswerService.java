package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerService {


    List<AnswerDto> findAll();

    Optional<AnswerDto> findByAnswer(String answer);

    List<AnswerDto> findAllByQuestionId (UUID uuid);

    Answer saveOrUpdate(@NotNull AnswerDto answerDto);


}
