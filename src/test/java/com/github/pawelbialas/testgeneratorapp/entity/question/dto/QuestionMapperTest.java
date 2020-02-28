package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QuestionMapperImpl.class, DateMapper.class})
class QuestionMapperTest {

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void dtoToObject() {
    }

    @Test
    void objectToDto() {
    }
}