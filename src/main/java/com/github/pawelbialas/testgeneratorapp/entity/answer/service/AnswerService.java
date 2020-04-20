package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerService {


    Optional<AnswerDto> findByAnswer(String answer);

    Optional<AnswerDto> findByQuestionId (UUID uuid);

    Answer saveOrUpdate(AnswerDto answerDto);

}
