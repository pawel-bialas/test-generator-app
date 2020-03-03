package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface AnswerMapper {


    Answer dtoToObject (AnswerDto answerDto);

    AnswerDto objectToDto (Answer answer);
}
