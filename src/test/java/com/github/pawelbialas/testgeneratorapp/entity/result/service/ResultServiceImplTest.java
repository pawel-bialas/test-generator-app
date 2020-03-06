package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResultServiceImplTest {

    @Mock
    SkillTestRepository skillTestRepository;
    @Mock
    ResultMapper mapper;
    @Mock
    ResultRepository resultRepository;
    @InjectMocks
    ResultServiceImpl resultService;



    Result result;

    Question question1;
    Question question2;

    Answer answer1;
    Answer answer2;

    SkillTest skillTest;

    @Before
    public void setUp()  {
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
                () -> assertThat(skillTest.getQuestions().size()).isEqualTo(2),
                () -> assertThat(skillTest.getQuestions().get(0)).isEqualTo(question2)
        );
    }

    @Test
    public void testNameToChange() {
        //Given


        //When


        //Then
        

    }
}