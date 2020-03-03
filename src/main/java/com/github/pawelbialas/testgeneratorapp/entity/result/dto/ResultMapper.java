package com.github.pawelbialas.testgeneratorapp.entity.result.dto;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.*;

@Mapper( componentModel = "spring",
        disableSubMappingMethodsGeneration = true,
        uses = {DateMapper.class, SkillTestMapper.class, ContestantMapper.class})
public interface ResultMapper {


    Result dtoToObject (ResultDto resultDto);

    @InheritInverseConfiguration
    ResultDto objectToDto (Result result, @Context CycleAvoidingMappingContext context);
}
