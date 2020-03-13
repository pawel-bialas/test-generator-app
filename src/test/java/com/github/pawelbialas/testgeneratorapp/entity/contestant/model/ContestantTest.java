package com.github.pawelbialas.testgeneratorapp.entity.contestant.model;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Contestant.class})
class ContestantTest {


    Contestant contestant;

    Result result;

    SkillTest skillTest;


    @BeforeEach
    void setUp() {
        contestant = Contestant.builder()
                .contestantNumber("1234")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .version(1L)
                .results(new ArrayList<Result>())
                .skillTests(new ArrayList<SkillTest>())
                .build();

        skillTest = SkillTest.builder()
                .questions(new ArrayList<Question>())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .version(1L)
                .id(UUID.randomUUID())
                .build();

        result = Result.builder()
//                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
//                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .score(12)
//                .id(UUID.randomUUID())
//                .version(1L)
                .build();

        assertAll(
                () -> assertThat(contestant).isNotNull()
        );
    }

    @Test
    void addTest() {
        //When
        contestant.addTest(skillTest);
        //Then
        assertAll(
                () -> assertThat(contestant.getSkillTests().size()).isEqualTo(1),
                () -> assertThat(contestant.getSkillTests().get(0)).isEqualTo(skillTest),
                () -> assertThat(skillTest.getContestant()).isEqualTo(contestant)
        );
    }

    @Test
    void removeTest() {
        //When
        contestant.addTest(skillTest);

        assertAll(
                () -> assertThat(contestant.getSkillTests().size()).isEqualTo(1),
                () -> assertThat(contestant.getSkillTests().get(0)).isEqualTo(skillTest)
        );

        contestant.removeTest(skillTest);
        //Then
        assertAll(
                () -> assertThat(contestant.getSkillTests().size()).isEqualTo(0),
                () -> assertThat(contestant.getSkillTests()).isNullOrEmpty(),
                () -> assertThat(skillTest.getContestant()).isNull()
        );
    }

    @Test
    public void addResult() {
        // When
        contestant.addResult(result);
        // Then
        assertAll(
                () -> assertThat(contestant.getResults().size()).isEqualTo(1),
                () -> assertThat(contestant.getResults().get(0)).isEqualTo(result)
        );

    }

    @Test
    public void removeResult() {
        // When
        contestant.addResult(result);
        assertAll(
                () -> assertThat(contestant.getResults().size()).isEqualTo(1),
                () -> assertThat(contestant.getResults().get(0)).isEqualTo(result)
        );

        contestant.removeResult(result);
        // Then
        assertAll(
                () -> assertThat(contestant.getResults().size()).isEqualTo(0),
                () -> assertThat(contestant.getResults()).isNullOrEmpty(),
                () -> assertThat(result.getContestant()).isNull()
        );
    }


}