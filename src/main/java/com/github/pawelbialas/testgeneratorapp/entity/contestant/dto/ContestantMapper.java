package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        disableSubMappingMethodsGeneration = true,
        uses = {DateMapper.class, ResultMapper.class, SkillTestMapper.class})
public interface ContestantMapper {


    Contestant dtoToObject (ContestantDto contestantDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    ContestantDto objectToDto (Contestant contestant, @Context CycleAvoidingMappingContext context);
}
