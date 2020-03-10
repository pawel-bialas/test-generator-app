package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
//        disableSubMappingMethodsGeneration = true,
        uses = {DateMapper.class, QuestionMapper.class})
public interface AnswerMapper {


    Answer dtoToObject (AnswerDto answerDto);

    AnswerDto objectToDto (Answer answer);
}
