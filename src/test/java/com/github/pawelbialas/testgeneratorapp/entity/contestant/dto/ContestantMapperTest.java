package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContestantMapperImpl.class, SkillTestMapperImpl.class, ResultMapperImpl.class, DateMapper.class})
class ContestantMapperTest {

    @Autowired
    ContestantMapper contestantMapper;
    @Autowired
    SkillTestMapper skillTestMapper;
    @Autowired
    ResultMapper resultMapper;

    Contestant testContestant;

    SkillTest testSkillTest;

    Result testResult;

    @BeforeEach
    void setUp() {

        testContestant = Contestant.builder()
                .contestantNumber("1234")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .version(1L)
                .results(new ArrayList<Result>())
                .skillTests(new ArrayList<SkillTest>())
                .build();

        testSkillTest = SkillTest.builder()
                .questions(new ArrayList<Question>())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .version(1L)
                .id(UUID.randomUUID())
                .build();

        testResult = Result.builder()
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .score(12)
                .id(UUID.randomUUID())
                .version(1L)
                .build();


        testContestant.addResult(testResult);
        testContestant.addTest(testSkillTest);

        assertAll(
                () -> assertThat(testContestant.getContestantNumber()).isEqualTo("1234"),
                () -> assertThat(testContestant.getResults().get(0)).isEqualTo(testResult),
                () -> assertThat(testContestant.getSkillTests().get(0)).isEqualTo(testSkillTest),
                () -> assertThat(testContestant.getResults().get(0).getScore()).isEqualTo(12)
        );

    }

    @Test
    void dtoToObject() {
        ContestantDto contestantDto = contestantMapper.objectToDto(testContestant);

        SkillTestDto skillTestDto = skillTestMapper.objectToDto(testSkillTest);
        ResultDto resultDto = resultMapper.objectToDto(testResult);


    }

    @Test
    void objectToDto() {

        //When
        ContestantDto contestantDto = contestantMapper.objectToDto(testContestant);

        SkillTestDto skillTestDto = skillTestMapper.objectToDto(testSkillTest);
        ResultDto resultDto = resultMapper.objectToDto(testResult);

        contestantDto.addTest(skillTestDto);
        contestantDto.addResult(resultDto);



        //Then
        assertAll(
                () -> assertThat(contestantDto).isNotNull(),
                () -> assertThat(contestantDto.getContestantNumber()).isEqualTo(testContestant.getContestantNumber()),
                () -> assertThat(contestantDto.getId()).isEqualTo(testContestant.getId())

        );

    }
}