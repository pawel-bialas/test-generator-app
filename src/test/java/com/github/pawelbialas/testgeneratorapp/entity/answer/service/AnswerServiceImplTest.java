package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
class AnswerServiceImplTest {


    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerServiceImpl answerService;
    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;

    QuestionDto question1;
    QuestionDto question2;

    AnswerDto answer1;

    @BeforeEach
    void setUp() {
        question1 = QuestionDto.builder()
                .answers(new LinkedHashSet<>())
                .contents("testQuestion1")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .build();

        question2 = QuestionDto.builder()
                .answers(new LinkedHashSet<>())
                .contents("testQuestion1")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .build();

        answer1 = AnswerDto.builder()
                .answer("test1")
                .correct(true)
                .build();

    }

    @Test
    void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        //Given

        question1.addAnswer(answer1);
        QuestionDto savedQuestion1 = questionService.saveOrUpdate(question1);
        questionService.saveOrUpdate(savedQuestion1);
        questionService.saveOrUpdate(savedQuestion1);
        questionService.saveOrUpdate(savedQuestion1);
        QuestionDto anotherSavedQuestion1 = questionService.saveOrUpdate(savedQuestion1);
        ArrayList<AnswerDto> answerDtos = new ArrayList<>(anotherSavedQuestion1.getAnswers());
        //When
        List<AnswerDto> answers = answerService.findAll();
        //Then
        assertAll(
                () -> assertThat(answers.size()).isEqualTo(1),
                () -> assertThat(answers.get(0).getId()).isEqualTo(answerDtos.get(0).getId()),
                () -> assertThat(question1.getAnswers().size()).isEqualTo(1)
        );
    }
}
