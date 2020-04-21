package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.exception.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultServiceImplTest {


    @Autowired
    ResultServiceImpl resultService;
    @Autowired
    SkillTestMapper testMapper;

    @MockBean
    SkillTestRepository repository;

    Result result;

    QuestionDto question1;
    QuestionDto question2;
    QuestionDto question3;
    QuestionDto question4;

    AnswerDto answer1;
    AnswerDto answer2;
    AnswerDto answer3;
    AnswerDto answer4;

    SkillTestDto skillTest;

    @Before
    public void setUp() {
        question1 = QuestionDto.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        question2 = QuestionDto.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        question3 = QuestionDto.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion2")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        question4 = QuestionDto.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion2")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        answer1 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(true)
                .answer("answer1")
                .question(null)
                .build();

        answer2 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(false)
                .answer("answer2")
                .question(null)
                .build();

        answer3 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(false)
                .answer("answer3")
                .question(null)
                .build();

        answer4 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(false)
                .answer("answer4")
                .question(null)
                .build();

        question1.addAnswer(answer1);
        question1.addAnswer(answer2);

        question2.addAnswer(answer1);
        question2.addAnswer(answer2);

        skillTest = SkillTestDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .contestant(new ContestantDto())
                .result(new ResultDto())
                .questions(new ArrayList<>())
                .testStatus("BASE")
                .build();

        List<QuestionDto> questions = skillTest.getQuestions();

        questions.add(question2);
        questions.add(question1);

        skillTest.setQuestions(questions);

        assertAll(
                () -> assertThat(resultService).isNotNull(),
                () -> assertThat(skillTest.getQuestions().size()).isEqualTo(2),
                () -> assertThat(skillTest.getQuestions().get(0)).isEqualTo(question2)
        );
    }


    @Test
    public void given_2TestsWithTheSameAnswers_Then_ShouldReturn_100() {
        // Given
        SkillTestDto otherSkillTest = skillTest;
        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext())));
        // When

        Float score = resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
//        Integer score = resultService.checkAnswers(skillTest, otherSkillTest);
        System.out.println(score);
        // Then
        assertAll(
                () -> assertThat(score).isEqualTo(100F)
        );

    }

    @Test
    public void given_2TestsWithDifferentAnswers_Then_ShouldReturn_50() {
        // Given
        SkillTestDto otherSkillTest = skillTest;

        otherSkillTest.setQuestions(new ArrayList<>());

        AnswerDto otherAnswer1 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(false)
                .answer("answer2")
                .question(null)
                .build();

        AnswerDto otherAnswer2 = AnswerDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .correct(true)
                .answer("answer2")
                .question(null)
                .build();

        //Both otherAnswer 1 and 2 are now quite opposites of the base tests

        question1.setAnswers(new ArrayList<>());
        question1.addAnswer(otherAnswer1);
        question1.addAnswer(otherAnswer2);

        ArrayList<QuestionDto> questions = new ArrayList<>();
        questions.add(question2);
        questions.add(question1);

        otherSkillTest.setQuestions(questions);

        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext())));

        //When
        Float score = resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
        System.out.println(score);
        // Then
        assertAll(
                () -> assertThat(score).isEqualTo(50F)
        );

    }

    @Test
    public void given_2TestsWithDifferentQuestionsContent_Then_Should_ThrowAnException() {
        // This test will prove questionIntegrityValidator
        // Given
        List<QuestionDto> questions = new ArrayList<>();
        questions.add(question4);
        questions.add(question3);

        SkillTestDto otherSkillTest = SkillTestDto.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .contestant(new ContestantDto())
                .result(new ResultDto())
                .questions(new ArrayList<>())
                .testStatus("BASE")
                .build();

        otherSkillTest.setQuestions(questions);
        //When
        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext())));
        //Then
        try {
            resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
            assertThat(e.getMessage()).isEqualTo("ResultService: test integrity error");
        }
    }

    @Test
    public void given_2TestsWithDifferentAnswerContent_Then_Should_ThrowAnException() {
        // This test will prove answerIntegrityValidator
        // Given

        SkillTestDto otherSkillTest = skillTest;
        QuestionDto otherQuestion = question1;

        otherQuestion.setAnswers(new ArrayList<>());
        otherQuestion.addAnswer(answer1);
        otherQuestion.addAnswer(answer3);

        otherSkillTest.setQuestions(new ArrayList<>());
        ArrayList<QuestionDto> questions = new ArrayList<>();
        questions.add(question2);
        questions.add(otherQuestion);
        otherSkillTest.setQuestions(questions);
        //When
        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext())));

        //Then
        try {
            resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
            assertThat(e.getMessage()).isEqualTo("ResultService: test integrity error");
        }

    }

    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given

        // When

        // Then

    }
}
