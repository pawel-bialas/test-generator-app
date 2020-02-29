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
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .id(UUID.randomUUID())
                .version(1L)
                .build();

        skillTestDto = SkillTestDto.builder()
                .questions(new ArrayList<QuestionDto>())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .version(1L)
                .id(UUID.randomUUID())
                .build();

        resultDto = ResultDto.builder()
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .score(12)
                .id(UUID.randomUUID())
                .version(1L)
                .build();
    }

    @Test
    void addTest() {
        //When
        contestantDto.addTest(skillTestDto);

        //Then
        assertAll(
                () -> assertThat(contestantDto.getSkillTests().size()).isEqualTo(1)

        );
    }

    @Test
    void removeTest() {
    }

    @Test
    void addResult() {
    }

    @Test
    void removeResult() {
    }
}