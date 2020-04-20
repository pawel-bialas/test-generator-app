package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillTestService {

    SkillTest saveOrUpdate(@NotNull SkillTestDto skillTestDto);

    List<SkillTestDto> findAllByContestant(ContestantDto contestant);

    List<SkillTestDto> findAllByContestantNumber(String contestantNumber);

    SkillTestDto createNewTest(String contestantNumber, List<TestParameter> testParams);

    Optional<SkillTestDto> findTestByUUID(UUID testUUID);

}
