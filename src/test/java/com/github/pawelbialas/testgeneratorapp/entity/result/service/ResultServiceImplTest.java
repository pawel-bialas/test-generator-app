package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.result.ResultServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
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

    @MockBean
    SkillTestRepository repository;

    Result result;

    Question question1;
    Question question2;
    Question question3;
    Question question4;

    Answer answer1;
    Answer answer2;
    Answer answer3;
    Answer answer4;

    SkillTest skillTest;

    @Before
    public void setUp() {
        question1 = Question.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion1")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech(MainTech.JAVA)
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
                .mainTech(MainTech.JAVA)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();

         question3 = Question.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion2")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech(MainTech.JAVA)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();

         question4 = Question.builder()
                .answers(new ArrayList<>())
                .contents("testQuestion2")
                .version(1L)
                .id(UUID.randomUUID())
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech(MainTech.JAVA)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .build();

        answer1 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(true)
                .answer("answer1")
                .question(null)
                .build();

        answer2 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(false)
                .answer("answer2")
                .question(null)
                .build();

        answer3 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(false)
                .answer("answer3")
                .question(null)
                .build();

        answer4 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(false)
                .answer("answer4")
                .question(null)
                .build();

        question1.addAnswer(answer1);
        question1.addAnswer(answer2);

        question2.addAnswer(answer1);
        question2.addAnswer(answer2);

        skillTest = SkillTest.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .contestant(new Contestant())
                .result(new Result())
                .questions(new ArrayList<>())
                .testStatus(TestStatus.BASE)
                .build();

        List<Question> questions = skillTest.getQuestions();

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
    public void given_testWith2CorrectAnswers_Then_ShouldReturn2() {

        //When
        Integer maxScore = resultService.calculateMaxScore(skillTest);
        System.out.println(maxScore);

        //Then
        assertAll(
                () -> assertThat(maxScore).isEqualTo(2)
        );
    }

    @Test
    public void given_2TestsWithTheSameAnswers_Then_ShouldReturn_100() {
        // Given
        SkillTest otherSkillTest = skillTest;
        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(skillTest));
        // When

        Integer score = resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
//        Integer score = resultService.checkAnswers(skillTest, otherSkillTest);
        System.out.println(score);
        // Then
        assertAll(
                () -> assertThat(score).isEqualTo(100)
        );

    }

    @Test
    public void given_2TestsWithDifferentAnswers_Then_ShouldReturn_1() {
        // Given
        SkillTest otherSkillTest = skillTest;

        otherSkillTest.setQuestions(new ArrayList<>());

        Answer otherAnswer1 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(false)
                .answer("answer2")
                .question(null)
                .build();

        Answer otherAnswer2 = Answer.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .correct(true)
                .answer("answer2")
                .question(null)
                .build();

        //Both otherAnswer 1 and 2 are now quite opposites of the base tests

        question1.setAnswers(new ArrayList<>());
        question1.addAnswer(otherAnswer1);
        question1.addAnswer(otherAnswer2);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question2);
        questions.add(question1);

        otherSkillTest.setQuestions(questions);

        when(repository.findById(skillTest.getId())).thenReturn(Optional.of(skillTest));

        //When
        Integer score = resultService.resolveTest(UUID.randomUUID(), skillTest.getId(), otherSkillTest);
        System.out.println(score);
        // Then
        assertAll(
                () -> assertThat(score).isEqualTo(50)
        );

    }

    @Test
    public void given_2TestsWithDifferentQuestionsContent_Then_Should_ThrowAnException() {
        // This test will prove questionIntegrityValidator
        // Given
        List<Question> questions = new ArrayList<>();
        questions.add(question4);
        questions.add(question3);

        SkillTest otherSkillTest = SkillTest.builder()
                .id(UUID.randomUUID())
                .version(1L)
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .contestant(new Contestant())
                .result(new Result())
                .questions(new ArrayList<>())
                .testStatus(TestStatus.BASE)
                .build();

        otherSkillTest.setQuestions(questions);
        //Then
        try {
            resultService.checkAnswers(skillTest, otherSkillTest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResultServiceException.class);
            assertThat(e.getMessage()).isEqualTo("ResultServiceImpl message name to change");
        }
    }

    @Test
    public void given_2TestsWithDifferentAnswerContent_Then_Should_ThrowAnException() {
        // This test will prove answerIntegrityValidator
        // Given

        SkillTest otherSkillTest = skillTest;
        Question otherQuestion = question1;

        otherQuestion.setAnswers(new ArrayList<>());
        otherQuestion.addAnswer(answer1);
        otherQuestion.addAnswer(answer3);

        otherSkillTest.setQuestions(new ArrayList<>());
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question2);
        questions.add(otherQuestion);
        otherSkillTest.setQuestions(questions);
        //When


        //Then
        try {
            resultService.checkAnswers(skillTest, otherSkillTest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResultServiceException.class);
            assertThat(e.getMessage()).isEqualTo("ResultServiceImpl message name to change");
        }

    }
}