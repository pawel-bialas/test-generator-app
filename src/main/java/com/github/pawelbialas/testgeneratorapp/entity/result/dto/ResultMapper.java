package com.github.pawelbialas.testgeneratorapp.entity.result.dto;


import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring",
        uses = {DateMapper.class, QuestionMapper.class, AnswerMapper.class, SkillTestMapper.class, ContestantMapper.class})
public interface ResultMapper {

    ResultDto objectToDto (Result result, @Context CycleAvoidingMappingContext context);
    @InheritInverseConfiguration(name = "objectToDto")
    Result dtoToObject (ResultDto resultDto, @Context CycleAvoidingMappingContext context);





}
