package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
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
@SpringBootTest(classes = {AnswerMapperImpl.class, QuestionMapperImpl.class, DateMapper.class})
class QuestionMapperTest {


   Question question;

   Answer answer1;
   Answer answer2;

   @Autowired
   QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        question = Question.builder()
                .contents("test123")
                .answers(new ArrayList<Answer>())
                .id(UUID.randomUUID())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .mainTech("Java")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Java")
                .build();

        answer1 = Answer.builder()
                .id(UUID.randomUUID())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .correct(true)
                .answer("test1")
                .build();

        answer2 = Answer.builder()
                .id(UUID.randomUUID())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .correct(true)
                .answer("test2")
                .build();

        question.addAnswer(answer1);
        question.addAnswer(answer2);

        assertAll(
                () -> assertThat(question.getAnswers().size()).isEqualTo(2),
                () -> assertThat(answer1.getQuestion()).isEqualTo(question),
                () -> assertThat(answer2.getQuestion()).isEqualTo(question)
        );
    }

    @Test
    void dtoToObject() {
        //When
        QuestionDto questionDto = questionMapper.objectToDto(question, contextProvider());
        assertAll(
                () -> assertThat(questionDto.getAnswers().size()).isEqualTo(2),
                () -> assertThat(questionDto.getId()).isEqualTo(question.getId()),
                () -> assertThat(questionDto.getAnswers().get(0).getAnswer()).isEqualTo(answer1.getAnswer())
        );
        Question result = questionMapper.dtoToObject(questionDto, contextProvider());

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(questionDto.getId()),
                () -> assertThat(result.getAnswers().size()).isEqualTo(2),
                () -> assertThat(result.getAnswers().get(0)).isEqualTo(answer1)
        );
    }

    @Test
    void objectToDto() {
        //When
        QuestionDto questionDto = questionMapper.objectToDto(question, contextProvider());
        System.out.println(questionDto);
        //Then
        assertAll(
                () -> assertThat(questionDto.getAnswers().size()).isEqualTo(2),
                () -> assertThat(questionDto.getId()).isEqualTo(question.getId()),
                () -> assertThat(questionDto.getAnswers().get(0).getAnswer()).isEqualTo(answer1.getAnswer()),
                () -> assertThat(questionDto.getAnswers().get(0)).isInstanceOf(AnswerDto.class),
                () -> assertThat(questionDto).isInstanceOf(QuestionDto.class)
        );
    }
}
