package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface QuestionService {

    Question saveOrUpdate(@NotNull QuestionDto questionDto);

    List<QuestionDto> findAll ();

    List<QuestionDto> findAllByMainTech(MainTech mainTech);

    List<QuestionDto> findAllByMainTechAndSpecificTech(MainTech mainTech, String specificTech);

    List<QuestionDto> findAllByMainTechAndSkillLevel(MainTech mainTech, SkillLevel skillLevel);

    List<QuestionDto> findAllByMainTechAndSkillLevelAndSpecificTech(MainTech mainTech, String specificTech, SkillLevel skillLevel);
}
