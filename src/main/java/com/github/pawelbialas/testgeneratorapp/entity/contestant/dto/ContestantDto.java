package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.BaseItem;
import lombok.*;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContestantDto extends BaseItem {

    private String contestantNumber;
    private List<SkillTestDto> skillTests = new ArrayList<>();
    private List<ResultDto> results = new ArrayList<>();

    @Builder
    public ContestantDto (UUID id,
                          Long version,
                          OffsetDateTime createdDate,
                          OffsetDateTime lastModifiedDate,
                          String contestantNumber,
                          List<SkillTestDto> skillTestDtos,
                          List<ResultDto> resultDtos) {
        super(id, version, createdDate, lastModifiedDate);
        this.contestantNumber = contestantNumber;
        this.results = resultDtos;
        this.skillTests = skillTestDtos;
    }


    public void addTest (SkillTestDto skillTestDto) {
        skillTests.add(skillTestDto);
        skillTestDto.setContestant(this);
    }

    public void removeTest (SkillTestDto skillTestDto) {
        skillTests.remove(skillTestDto);
        skillTestDto.setContestant(null);
    }

    public void addResult (ResultDto resultDto) {
        results.add(resultDto);
        resultDto.setContestant(this);
    }

    public void removeResult (ResultDto resultDto) {
        results.remove(resultDto);
        resultDto.setContestant(null);
    }

    @Override
    public String toString() {
        return "ContestantDto{" +
                "contestantNumber='" + contestantNumber + '\'' +
                ", number of skillTests=" + skillTests +
                ", number of results=" + results +
                '}';
    }
}
