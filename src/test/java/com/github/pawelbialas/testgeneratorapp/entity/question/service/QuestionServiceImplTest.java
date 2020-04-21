package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapperImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.DateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    QuestionRepository repository;

    @Autowired
    QuestionServiceImpl service;

    @Autowired
    QuestionMapper mapper;


    QuestionDto question1;
    QuestionDto question2;

    @BeforeEach
    void setUp() {

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

        assertAll(
                () -> assertThat(question1.getContents()).isEqualTo(question2.getContents())
        );
    }

    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given
        Question savedQuestion1 = service.saveOrUpdate(question1);
        Question savedQuestion2 = service.saveOrUpdate(question2);

        service.saveOrUpdate(mapper.objectToDto(savedQuestion1, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion1, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion1, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion1, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion2, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion2, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion2, new CycleAvoidingMappingContext()));
        service.saveOrUpdate(mapper.objectToDto(savedQuestion2, new CycleAvoidingMappingContext()));
        // When
        Optional<QuestionDto> searchResult = service.findById(savedQuestion1.getId());


        List<QuestionDto> all = service.findAll();
        // Then
        assertAll(
                () -> assertThat(searchResult.isPresent()),
                () -> assertThat(searchResult.get().getId()).isEqualTo(savedQuestion1.getId()),
                () -> assertThat(all.size()).isEqualTo(2)
        );
    }
}
