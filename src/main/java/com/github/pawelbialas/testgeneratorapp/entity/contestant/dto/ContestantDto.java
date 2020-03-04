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
@EqualsAndHashCode(callSuper = false)
public class ContestantDto extends BaseItem {

    public String contestantNumber;
    public List<SkillTestDto> skillTests = new ArrayList<>();
    public List<ResultDto> results = new ArrayList<>();

    @Builder
    public ContestantDto (UUID id,
                          Long version,
                          OffsetDateTime createdDate,
                          OffsetDateTime lastModifiedDate,
                          String contestantNumber,
                          List<SkillTestDto> skillTests,
                          List<ResultDto> results) {
        super(id, version, createdDate, lastModifiedDate);
        this.contestantNumber = contestantNumber;
        this.results = results;
        this.skillTests = skillTests;
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

}
