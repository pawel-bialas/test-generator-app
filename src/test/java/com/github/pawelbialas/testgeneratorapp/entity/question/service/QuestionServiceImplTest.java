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

@WebMvcTest(QuestionServiceImpl.class)
class QuestionServiceImplTest {


    @MockBean
    QuestionRepository repository;

    @MockBean
    EntityManagerFactory emf;

    @Autowired
    MockMvc mockMvc;

    @Value("${csv.location.test}")
    String csvPath;

    QuestionServiceImpl questionServiceImpl;

    @BeforeEach
    void setUp() {

    }



}