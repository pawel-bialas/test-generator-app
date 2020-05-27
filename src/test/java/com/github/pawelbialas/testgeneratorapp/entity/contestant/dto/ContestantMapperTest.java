package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AnswerMapperImpl.class, QuestionMapperImpl.class, ContestantMapperImpl.class, SkillTestMapperImpl.class, ResultMapperImpl.class, DateMapper.class})
class ContestantMapperTest {


    @Autowired
    ContestantMapper contestantMapper;
    @Autowired
    SkillTestMapper skillTestMapper;
    @Autowired
    ResultMapper resultMapper;

    Contestant contestant;

    SkillTest skillTest;

    Result result;

    @BeforeEach
    void setUp() {

        contestant = Contestant.builder()
                .contestantNumber("1234")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .results(new ArrayList<Result>())
                .skillTests(new ArrayList<SkillTest>())
                .build();

        skillTest = SkillTest.builder()
                .testStatus(TestStatus.BASE)
                .questions(new ArrayList<Question>())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .result(new Result())
                .contestant(new Contestant())
                .build();

        result = Result.builder()
                .contestant(new Contestant())
                .score(12F)
                .skillTest(new SkillTest())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .build();


        contestant.addResult(result);
        contestant.addTest(skillTest);

        assertAll(
                () -> assertThat(contestant.getContestantNumber()).isEqualTo("1234"),
                () -> assertThat(contestant.getResults().get(0)).isEqualTo(result),
                () -> assertThat(contestant.getSkillTests().get(0)).isEqualTo(skillTest),
                () -> assertThat(contestant.getResults().get(0).getScore()).isEqualTo(12F)
        );

    }

    @Test
    void dtoToObject() {
        //When
        contestant.addTest(skillTest);
        contestant.addResult(result);
        ContestantDto contestantDto = contestantMapper.objectToDto(contestant, contextProvider());
        contestantDto.setSkillTests(new ArrayList<SkillTestDto>());
        contestantDto.addTest(skillTestMapper.objectToDto(contestant.getSkillTests().get(contestant.getSkillTests().size() - 1), contextProvider()));
        contestantDto.setResults(new ArrayList<ResultDto>());
        contestantDto.addResult(resultMapper.objectToDto(contestant.getResults().get(contestant.getResults().size() - 1), contextProvider()));

        //Then

        assertAll(
                () -> assertThat(contestantDto.getId()).isEqualTo(contestant.getId()),
                () -> assertThat(contestantDto.getContestantNumber()).isEqualTo("1234"),
                () -> assertThat(contestantDto.getSkillTests().size()).isEqualTo(1),
                () -> assertThat(contestantDto.getSkillTests().get(0).getId()).isEqualTo(skillTestMapper.objectToDto(skillTest, contextProvider()).getId())
        );




    }


}
