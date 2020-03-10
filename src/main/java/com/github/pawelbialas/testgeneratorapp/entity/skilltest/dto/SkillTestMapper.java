package com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class, ContestantMapper.class, QuestionMapper.class, ResultDto.class})
public interface SkillTestMapper {

    SkillTest dtoToObject (SkillTestDto skillTestDto);
    SkillTestDto objectToDto (SkillTest skillTest);

}
