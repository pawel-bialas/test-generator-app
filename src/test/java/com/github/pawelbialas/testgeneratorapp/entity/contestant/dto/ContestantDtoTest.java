package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContestantDto.class})
class ContestantDtoTest {


    ContestantDto contestantDto;

    SkillTestDto skillTestDto;

    ResultDto resultDto;

    @BeforeEach
    void setUp() {

        contestantDto = ContestantDto.builder()
                .contestantNumber("1234")
                .id(UUID.randomUUID())
                .results(new LinkedHashSet<>())
                .skillTests(new LinkedHashSet<>())
                .build();

        skillTestDto = SkillTestDto.builder()
                .questions(new LinkedHashSet<>())
                .id(UUID.randomUUID())
                .build();

        resultDto = ResultDto.builder()
                .score(12F)
                .build();
    }

    @Test
    void addTest() {
        //When
        contestantDto.addTest(skillTestDto);
        ArrayList<SkillTestDto> skillTests = new ArrayList<>(contestantDto.getSkillTests());
        //Then

        assertAll(
                () -> assertThat(contestantDto.getSkillTests().size()).isEqualTo(1),
                () -> assertThat(skillTests.get(0)).isEqualTo(skillTestDto)
        );


    }

    @Test
    void removeTest() {
        //When
        contestantDto.addTest(skillTestDto);
        ArrayList<SkillTestDto> skillTests = new ArrayList<>(contestantDto.getSkillTests());
        assertAll(
                () -> assertThat(contestantDto.getSkillTests().size()).isEqualTo(1),
                () -> assertThat(skillTests.get(0)).isEqualTo(skillTestDto),
                () -> assertThat(skillTestDto.getContestant()).isEqualTo(contestantDto)
        );
        contestantDto.removeTest(skillTestDto);
        //Then
        assertAll(
                () -> assertThat(contestantDto.getSkillTests()).isNullOrEmpty(),
                () -> assertThat(skillTestDto.getContestant()).isNull()
        );
    }

    @Test
    void addResult() {
        //When
        contestantDto.addResult(resultDto);
        //Then
        ArrayList<ResultDto> results = new ArrayList<>(contestantDto.getResults());
        assertAll(
                () -> assertThat(contestantDto.getResults().size()).isEqualTo(1),
                () -> assertThat(results.get(0)).isEqualTo(resultDto)
        );
    }

    @Test
    void removeResult() {
        //When
        contestantDto.addResult(resultDto);
        ArrayList<ResultDto> results = new ArrayList<>(contestantDto.getResults());
        assertAll(
                () -> assertThat(contestantDto.getResults().size()).isEqualTo(1),
                () -> assertThat(results.get(0)).isEqualTo(resultDto),
                () -> assertThat(resultDto.getContestant()).isEqualTo(contestantDto)
        );
        contestantDto.removeResult(resultDto);
        //Then

        assertAll(
                () -> assertThat(contestantDto.getResults()).isNullOrEmpty(),
                () -> assertThat(resultDto.getContestant()).isNull()
        );

    }
}
