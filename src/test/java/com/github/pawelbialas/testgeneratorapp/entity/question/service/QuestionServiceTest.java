package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultService;
import com.github.pawelbialas.testgeneratorapp.entity.test.service.SkillTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

@WebMvcTest(QuestionService.class)
class QuestionServiceTest {


    @MockBean
    QuestionRepository repository;

    @MockBean
    EntityManagerFactory emf;

    @Autowired
    MockMvc mockMvc;

    @Value("${csv.location.test}")
    String csvPath;

    QuestionService questionService;

    @BeforeEach
    void setUp() {
       questionService = new QuestionService(repository, emf);

    }

    @Test
    public void basicSaveOrUpdateTest() {
        // Given

        questionService.readQuestionsFromCsv("F:\\Files\\source\\test-generator-app\\test-generator-app\\src\\test\\java\\resources\\input.csv");

        List<Question> all = questionService.findAll();

        System.out.println(all.size());
        all.forEach(System.out::println);
        // When

        // Then

    }


}