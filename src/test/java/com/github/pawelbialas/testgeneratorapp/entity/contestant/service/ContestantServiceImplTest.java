package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
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
class ContestantServiceImplTest {

    @Autowired
    ContestantServiceImpl contestantService;
    @Autowired
    ContestantMapper contestantMapper;


    ContestantDto contestantDto;
    SkillTestDto skillTestDto;
    ResultDto resultDto;

    @BeforeEach
    void setUp() {

        contestantDto = ContestantDto.builder()
                .contestantNumber("1234")
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .results(new ArrayList<>())
                .skillTests(new ArrayList<>())
                .build();

        skillTestDto = SkillTestDto.builder()
                .questions(new ArrayList<>())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

        resultDto = ResultDto.builder()
                .score(12F)
                .build();
    }


    @Test
    public void callingSaveOrUpdateMultipleTimesShouldAlwaysReturnSingleEntity() {
        // Given
        contestantDto.addTest(skillTestDto);
        contestantDto.addResult(resultDto);
        ContestantDto save = contestantService.saveOrUpdate(this.contestantDto);
        System.out.println("after 1st save");
        // When
        contestantService.saveOrUpdate(save);
        contestantService.saveOrUpdate(save);
        contestantService.saveOrUpdate(save);
        contestantService.saveOrUpdate(save);
        contestantService.saveOrUpdate(save);
        System.out.println("after last save");

        List<ContestantDto> all = contestantService.findAll();
//         Then
        assertAll (
                () -> assertThat(all.size()).isEqualTo(1),
                () -> assertThat(all.get(0).getId()).isEqualTo(save.getId())
        );
    }
}
