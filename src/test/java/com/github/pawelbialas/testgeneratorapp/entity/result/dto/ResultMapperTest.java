package com.github.pawelbialas.testgeneratorapp.entity.result.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.UUID;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResultMapperImpl.class, QuestionMapperImpl.class, AnswerMapperImpl.class, ContestantMapperImpl.class, SkillTestMapperImpl.class, DateMapper.class})
class ResultMapperTest {

    Result result;

    SkillTest skillTest;

    Contestant contestant;

    @Autowired
    ResultMapper resultMapper;

    @BeforeEach
    void setUp() {


       result = Result.builder()
                .id(UUID.randomUUID())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .score(12F)
                .contestant(null)
                .skillTest(null)
                .build();

        skillTest = SkillTest.builder()
                .questions(new LinkedHashSet<>())
                .id(UUID.randomUUID())
                .result(null)
                .contestant(null)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .testStatus(TestStatus.BASE)
                .build();

        contestant = Contestant.builder()
                .skillTests(new LinkedHashSet<>())
                .results(new LinkedHashSet<>())
                .id(UUID.randomUUID())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                .contestantNumber("1234")
                .build();

        contestant.addTest(skillTest);
        contestant.addResult(result);

        skillTest.setResult(result);
        result.setSkillTest(skillTest);

        ArrayList<Result> results = new ArrayList<>(contestant.getResults());
        ArrayList<SkillTest> skillTests = new ArrayList<>(contestant.getSkillTests());

        assertAll(
                () -> assertThat(skillTests.get(0)).isEqualTo(skillTest),
                () -> assertThat(results.get(0)).isEqualTo(result),
                () -> assertThat(result.getSkillTest()).isEqualTo(skillTest),
                () -> assertThat(skillTest.getResult()).isEqualTo(result)
        );
    }

    @Test
    void objectToDto() {

        ResultDto resultDto = resultMapper.objectToDto(result, contextProvider());

        assertAll(
                () -> assertThat(resultDto.getId()).isEqualTo(result.getId())
        );
        System.out.println(result);
        System.out.println(resultDto);


    }
}
