package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuestionMapperImpl.class, AnswerMapperImpl.class, DateMapper.class})
class AnswerMapperTest {



    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;

    Question testQuestion;

    Answer testAnswer;

    @BeforeEach
    void setUp() {
        testQuestion = Question.builder()
                .answers(new ArrayList<Answer>())
                .contents("testQuestionContent")
                .mainTech(MainTech.JAVA)
                .specificTech("JPA")
                .skillLevel(SkillLevel.ENTRY)
                .version(1L)
                .id(UUID.randomUUID())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))

                .build();

        testAnswer = Answer.builder()
                .question(testQuestion)
                .answer("testAnswer")
                .correct(false)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .version(1L)
                .id(UUID.randomUUID())

                .build();

        testQuestion.addAnswer(testAnswer);

        assertAll(
                () -> assertThat(testQuestion.getAnswers().size()).isEqualTo(1),
                () -> assertThat(testQuestion.getAnswers().get(0)).isEqualTo(testAnswer),
                () -> assertThat(testAnswer.getQuestion()).isEqualTo(testQuestion),
                () -> assertThat(testAnswer.getAnswer()).isEqualTo("testAnswer"),
                () -> assertThat(testQuestion.getContents()).isEqualTo("testQuestionContent")
        );
    }

    @Test
    void dtoToObject() {

        //When

        AnswerDto answerDto = answerMapper.objectToDto(testAnswer);
        QuestionDto questionDto = questionMapper.objectToDto(testQuestion);
        questionDto.addAnswer(answerDto);

        Answer mappedAnswer = answerMapper.dtoToObject(answerDto);
        Question mappedQuestion = questionMapper.dtoToObject(questionDto);
        mappedQuestion.addAnswer(mappedAnswer);
        //Then

        assertAll(
                () -> assertThat(mappedAnswer.getId()).isEqualTo(answerDto.getId()),
                () -> assertThat(mappedAnswer.getQuestion()).isEqualTo(mappedQuestion),
                () -> assertThat(mappedQuestion.getAnswers().get(0)).isEqualTo(mappedAnswer),
                () -> assertThat(mappedAnswer.getAnswer()).isEqualTo(answerDto.getAnswer())
        );

    }

    @Test
    void objectToDto() {

        //When
        AnswerDto answerDto = answerMapper.objectToDto(testAnswer);
        QuestionDto questionDto = questionMapper.objectToDto(testQuestion);

        questionDto.addAnswer(answerDto);

        //Then
        assertAll(
                () -> assertThat(answerDto.getId()).isEqualTo(testAnswer.getId()),
                () -> assertThat(answerDto.getQuestionDto()).isEqualTo(questionDto),
                () -> assertThat(answerDto.getAnswer()).isEqualTo("testAnswer")
        );
    }
}