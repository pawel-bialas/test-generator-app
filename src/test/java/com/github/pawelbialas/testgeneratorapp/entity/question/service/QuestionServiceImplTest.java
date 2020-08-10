package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                .answers(new LinkedHashSet<>())
                .contents("testQuestion1")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .build();

        question2 = QuestionDto.builder()
                .answers(new LinkedHashSet<>())
                .contents("testQuestion1")
                .skillLevel(SkillLevel.ENTRY)
                .specificTech("Core")
                .mainTech("Java")
                .build();

        assertAll(
                () -> assertThat(question1.getContents()).isEqualTo(question2.getContents())
        );
    }

    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given
        QuestionDto savedQuestion1 = service.saveOrUpdate(question1);
        System.out.println(savedQuestion1);
        QuestionDto savedQuestion2 = service.saveOrUpdate(question2);
        System.out.println(savedQuestion2);

       service.saveOrUpdate(savedQuestion1);
       service.saveOrUpdate(savedQuestion1);
       service.saveOrUpdate(savedQuestion1);
       service.saveOrUpdate(savedQuestion2);
       service.saveOrUpdate(savedQuestion2);
       service.saveOrUpdate(savedQuestion2);
        // When
        Optional<QuestionDto> searchResult = service.findById(savedQuestion1.getId());


        List<QuestionDto> all = service.findAll();
        // Then
        assertAll(
                () -> assertThat(searchResult.isPresent()),
                () -> assertThat(searchResult.get().getId()).isEqualTo(savedQuestion1.getId()),
                () -> assertThat(all.size()).isEqualTo(2),
                () -> assertThat(all.get(0).getId()).isEqualTo(savedQuestion1.getId()),
                () -> assertThat(all.get(1).getId()).isEqualTo(savedQuestion2.getId())
        );
    }
}
