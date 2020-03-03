package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface ContestantMapper {

    Contestant dtoToObject (ContestantDto contestantDto);

    ContestantDto objectToDto (Contestant contestant);
}
