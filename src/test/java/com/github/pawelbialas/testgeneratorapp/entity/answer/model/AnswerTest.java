package com.github.pawelbialas.testgeneratorapp.entity.answer.model;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
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
@SpringBootTest(classes = {Answer.class, Question.class})
class AnswerTest {


    Answer answer;

    Question question;

    @BeforeEach
    void setUp() {

         answer = Answer.builder()
                .answer("test1")
                .correct(true)
                .version(1L)
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .id(UUID.randomUUID())
                .build();

         question = Question.builder()
                 .mainTech(MainTech.JAVA)
                 .specificTech("Core")
                 .skillLevel(SkillLevel.ENTRY)
                 .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                 .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                 .id(UUID.randomUUID())
                 .version(1L)
                 .contents("test1")
                 .answers(new ArrayList<Answer>())
                 .build();

    }


    @Test
    public void simplePropertiesTest() {
        // When
        question.addAnswer(answer);
        // Then
        assertAll(
                () -> assertThat(answer.getQuestion()).isEqualTo(question),
                () -> assertThat(answer.getAnswer()).isEqualTo("test1"),
                () -> assertThat(answer.getCorrect()).isTrue()
        );
    }
}