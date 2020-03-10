package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class, ResultMapper.class, SkillTestMapper.class, QuestionMapper.class, AnswerMapper.class})
public interface ContestantMapper {


    Contestant dtoToObject (ContestantDto contestantDto);

    ContestantDto objectToDto (Contestant contestant);
}
