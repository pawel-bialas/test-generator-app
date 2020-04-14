package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
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
@SpringBootTest(classes = {Question.class})
class QuestionTest {

    Question question;

    Answer answer1;

    Answer answer2;

    @BeforeEach
    void setUp() {

         question = Question.builder()
                .contents("test123")
                .answers(new ArrayList<Answer>())
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .mainTech(String.JAVA)
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Java")
                .build();

         answer1 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .correct(true)
                .answer("test1")
                .build();

        answer2 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .correct(true)
                .answer("test2")
                .build();
    }

    @Test
    void addAnswer() {
        //When
        question.addAnswer(answer1);
        question.addAnswer(answer2);

        //Then
        assertAll(
                () -> assertThat(question.getAnswers().size()).isEqualTo(2),
                () -> assertThat(answer1.getQuestion()).isEqualTo(question),
                () -> assertThat(answer2.getQuestion()).isEqualTo(question)
        );
    }

    @Test
    void removeAnswer() {
    }
}
