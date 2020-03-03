package com.github.pawelbialas.testgeneratorapp.entity.result.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContestantMapperImpl.class, ResultMapperImpl.class, DateMapper.class})
class ResultMapperTest {

    @Autowired
    ResultMapper resultMapper;

    @Autowired
    ContestantMapper contestantMapper;

    Result result;

    SkillTest skillTest;

    Contestant contestant;

    @BeforeEach
    void setUp() {

        result = Result.builder()
                .id(UUID.randomUUID())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .score(12)
                .version(1L)
                .contestant(null)
                .skillTest(null)
                .build();

        skillTest = SkillTest.builder()
                .questions(new ArrayList<Question>())
                .id(UUID.randomUUID())
                .version(1L)
                .result(null)
                .contestant(null)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .status(TestStatus.BASE)
                .build();

        contestant = Contestant.builder()
                .skillTests(new ArrayList<SkillTest>())
                .results(new ArrayList<Result>())
                .version(1L)
                .id(UUID.randomUUID())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .contestantNumber("1234")
                .build();

        contestant.addTest(skillTest);
        contestant.addResult(result);

        skillTest.setResult(result);
        result.setSkillTest(skillTest);

        assertAll(
                () -> assertThat(contestant.getSkillTests().get(0)).isEqualTo(skillTest),
                () -> assertThat(contestant.getResults().get(0)).isEqualTo(result),
                () -> assertThat(result.getSkillTest()).isEqualTo(skillTest),
                () -> assertThat(skillTest.getResult()).isEqualTo(result)
        );
    }

    @Test
    void dtoToObject() {
    }

    @Test
    void objectToDto() {

        ResultDto resultDto = resultMapper.objectToDto(result);

        System.out.println(resultDto);


    }
}