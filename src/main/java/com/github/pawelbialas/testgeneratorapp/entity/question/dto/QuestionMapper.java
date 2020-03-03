package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true,
        uses = {DateMapper.class, AnswerMapper.class})
public interface QuestionMapper {

    Question dtoToObject (QuestionDto questionDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    QuestionDto objectToDto (Question question, @Context CycleAvoidingMappingContext context);


}
