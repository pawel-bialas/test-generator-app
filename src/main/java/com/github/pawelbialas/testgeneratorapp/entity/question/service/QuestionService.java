package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {

    QuestionDto saveOrUpdate(@NotNull QuestionDto questionDto);

    Optional<QuestionDto> findById(UUID uuid);

    List<QuestionDto> findAll ();

    List<QuestionDto> findAllByMainTech(String mainTech);

    List<QuestionDto> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech);

    List<QuestionDto> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel);

    List<QuestionDto> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, java.lang.String specificTech, SkillLevel skillLevel);
}
