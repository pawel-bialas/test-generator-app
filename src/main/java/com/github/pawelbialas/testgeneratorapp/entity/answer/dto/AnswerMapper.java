package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {

    Answer dtoToObject (AnswerDto answerDto);

    AnswerDto objectToDto (Answer answer);
}
