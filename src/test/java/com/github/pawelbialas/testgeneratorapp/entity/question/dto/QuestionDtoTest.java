package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = {QuestionDto.class})
class QuestionDtoTest {

    QuestionDto question;

    AnswerDto answer1;

    AnswerDto answer2;

    @BeforeEach
    void setUp() {

        question = QuestionDto.builder()
                .contents("test123")
                .answers(new ArrayList<AnswerDto>())
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .mainTech("Java")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Java")
                .build();

        answer1 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(true)
                .answer("test1")
                .build();

        answer2 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
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
        //When

        question.addAnswer(answer1);
        question.addAnswer(answer2);
        assertAll(
                () -> assertThat(question.getAnswers().size()).isEqualTo(2),
                () -> assertThat(answer1.getQuestion()).isEqualTo(question),
                () -> assertThat(answer2.getQuestion()).isEqualTo(question)
        );
        question.removeAnswer(answer1);
        question.removeAnswer(answer2);
        //Then
        assertAll(
                () -> assertThat(question.getAnswers().size()).isEqualTo(0),
                () -> assertThat(question.getAnswers()).isNullOrEmpty(),
                () -> assertThat(answer1.getQuestion()).isNotEqualTo(question),
                () -> assertThat(answer2.getQuestion()).isNotEqualTo(question)
        );
    }
}
