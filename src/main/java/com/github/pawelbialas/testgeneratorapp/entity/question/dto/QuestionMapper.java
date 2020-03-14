package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class, AnswerMapper.class, ResultMapper.class, Contestant.class, SkillTestMapper.class})
public interface QuestionMapper {

    QuestionDto objectToDto (Question question, @Context CycleAvoidingMappingContext context);
    @InheritInverseConfiguration(name = "objectToDto")
    Question dtoToObject (QuestionDto questionDto, @Context CycleAvoidingMappingContext context);




}
