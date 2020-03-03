package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface QuestionMapper {

    Question dtoToObject (QuestionDto questionDto);

    QuestionDto objectToDto (Question question);


}
