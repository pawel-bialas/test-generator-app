package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class, QuestionMapper.class, SkillTestMapper.class, ContestantMapper.class, ResultMapper.class})
public interface AnswerMapper {

    AnswerDto objectToDto (Answer answer, @Context CycleAvoidingMappingContext context);
    @InheritInverseConfiguration(name = "objectToDto")
    Answer dtoToObject (AnswerDto answerDto, @Context CycleAvoidingMappingContext context);


}
