package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
@SpringBootTest
class SkillTestServiceImplTest {

    @Autowired
    SkillTestServiceImpl skillTestService;


    SkillTestDto skillTestDto;

    @BeforeEach
    void setUp() {

        skillTestDto = SkillTestDto.builder()
                .questions(new ArrayList<>())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .version(1L)
                .testStatus("BASE")
                .build();

    }


    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given

        SkillTestDto savedTest = skillTestService.saveOrUpdate(skillTestDto);

        //When
        skillTestService.saveOrUpdate(savedTest);
        skillTestService.saveOrUpdate(savedTest);
        skillTestService.saveOrUpdate(savedTest);
        skillTestService.saveOrUpdate(savedTest);

        List<SkillTestDto> all = skillTestService.findAll();
        //Then

        assertAll(
                () -> assertThat(all.size()).isEqualTo(1),
                () -> assertThat(all.get(0).getId()).isEqualTo(savedTest.getId())
        );

    }
}
