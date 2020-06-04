package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = false)
public class ContestantDto extends BaseItem {

    private String contestantNumber;
    private Set<SkillTestDto> skillTests = new LinkedHashSet<>();
    private Set<ResultDto> results = new LinkedHashSet<>();

    @Builder
    public ContestantDto (UUID id,
                          Long version,
                          OffsetDateTime createdDate,
                          OffsetDateTime lastModifiedDate,
                          String contestantNumber,
                          LinkedHashSet<SkillTestDto> skillTests,
                          LinkedHashSet<ResultDto> results) {
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

    @Override
    public String toString() {
        return "ContestantDto{" +
                "id='" + this.getId() + '\'' +
                "contestantNumber='" + contestantNumber + '\'' +
                ", skillTests=" + skillTests.size() +
                ", results=" + results.size() +
                '}';
    }
}
