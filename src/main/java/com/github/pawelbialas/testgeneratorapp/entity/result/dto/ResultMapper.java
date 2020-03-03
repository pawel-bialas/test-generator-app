package com.github.pawelbialas.testgeneratorapp.entity.result.dto;


import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY, componentModel = "spring", uses = DateMapper.class)
public interface ResultMapper {

    Result dtoToObject (ResultDto resultDto);

    ResultDto objectToDto (Result result);
}
