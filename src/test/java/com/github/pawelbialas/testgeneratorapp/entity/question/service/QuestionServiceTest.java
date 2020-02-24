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

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {


    @Mock
    QuestionRepository repository;

    @Mock
    EntityManagerFactory emf;

    @InjectMocks
    QuestionService service;

    @Value("${csv.location.test}")
    String csvPath;

    @BeforeEach
    void setUp() {
        service.readQuestionsFromCsv("F:\\Files\\source\\test-generator-app\\test-generator-app\\src\\test\\java\\resources\\input.csv");

        List<Question> all = service.findAll();

        all.forEach(System.out::println);
    }

    @Test
    public void basicSaveOrUpdateTest() {
        // Given

        // When

        // Then

    }


}