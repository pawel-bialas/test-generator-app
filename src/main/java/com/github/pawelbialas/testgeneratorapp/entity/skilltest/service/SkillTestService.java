package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;

import java.util.List;
import java.util.UUID;

public interface SkillTestService {


    List<SkillTestDto> findTestByContestantNumber(String contestantNumber);

    SkillTestDto createNewTest(String contestantNumber, List<TestParameter> testParams);

    SkillTestDto findTestByUUID(UUID testUUID);

}
