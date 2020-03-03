package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true,
        uses = {DateMapper.class, QuestionMapper.class})
public interface AnswerMapper {


    Answer dtoToObject (AnswerDto answerDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    AnswerDto objectToDto (Answer answer, @Context CycleAvoidingMappingContext context);
}
