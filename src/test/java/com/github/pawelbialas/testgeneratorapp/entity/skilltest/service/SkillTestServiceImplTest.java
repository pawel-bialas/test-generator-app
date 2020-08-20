package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
class SkillTestServiceImplTest {

    @Autowired
    SkillTestServiceImpl skillTestService;
    @Autowired
    SkillTestRepository skillTestRepository;
    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    SkillTestMapperImpl skillTestMapper;

    SkillTestDto skillTestDto;
    QuestionDto questionDto;
    AnswerDto answerDto;
    AnswerDto answerDto2;
    AnswerDto answerDto3;

    @BeforeEach
    void setUp() {

        skillTestDto = SkillTestDto.builder()
                .questions(new ArrayList<>())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .testStatus("BASE")
                .build();

        questionDto = QuestionDto.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .build();

        answerDto = AnswerDto.builder()
                .answer("test1")
                .correct(true)
                .build();

        answerDto2 = AnswerDto.builder()
                .answer("test2")
                .correct(true)
                .build();

        answerDto3 = AnswerDto.builder()
                .answer("test3")
                .correct(true)
                .build();

    }


    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given

        questionDto.addAnswer(answerDto);
        questionDto.addAnswer(answerDto2);
        questionDto.addAnswer(answerDto3);
        QuestionDto savedQuestion = questionService.saveOrUpdate(questionDto);

        SkillTestDto savedSkillTest = skillTestService.saveOrUpdate(skillTestDto);

        savedSkillTest.getQuestions().add(savedQuestion);

        SkillTestDto savedTest = skillTestService.saveOrUpdate(savedSkillTest);

        System.out.println("after 1st save");

        //When

        SkillTestDto savedTest2 = skillTestService.saveOrUpdate(savedTest);
        System.out.println("after 2nd save");

        SkillTestDto savedTest3 = skillTestService.saveOrUpdate(savedTest2);
        System.out.println("after 3rd save");

        SkillTestDto savedTest4 = skillTestService.saveOrUpdate(savedTest3);

        System.out.println("after 4th save");

        SkillTestDto savedTest5 = skillTestService.saveOrUpdate(savedTest4);
        System.out.println("after last save");

        List<SkillTestDto> all = skillTestService.findAll();
        System.out.println("result");
        System.out.println(all.get(0));
        System.out.println(all.get(0).getVersion());
        System.out.println("answer1: " + all.get(0).getQuestions().get(0).getAnswers().get(0));
        System.out.println("answer2: " + all.get(0).getQuestions().get(0).getAnswers().get(1));
        System.out.println("answer2: " + all.get(0).getQuestions().get(0).getAnswers().get(2));
        //Then

        assertAll(
                () -> assertThat(all.size()).isEqualTo(1),
                () -> assertThat(all.get(0).getId()).isEqualTo(savedTest.getId())
        );

    }
}
