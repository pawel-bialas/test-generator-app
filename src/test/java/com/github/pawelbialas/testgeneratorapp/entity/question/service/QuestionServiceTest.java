package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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