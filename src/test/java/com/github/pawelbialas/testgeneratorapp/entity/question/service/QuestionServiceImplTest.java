package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuestionRepository.class, QuestionMapperImpl.class, AnswerMapperImpl.class, EntityManagerFactory.class, DateMapper.class})
class QuestionServiceImplTest {


    Question question1;
    Question question2;

    @BeforeEach
    void setUp() {

        question1 = Question.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech(String.JAVA)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();

        question2 = Question.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech(String.JAVA)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();

        assertAll(
                () -> assertThat(question1.getContents()).isEqualTo(question2.getContents())
        );
    }

    @Test
    public void testNameToChange() {
        // Given

        // When

        // Then
        assertAll(
                () -> assertThat(question1.getContents()).isEqualTo(question2.getContents())

        );
    }
}
