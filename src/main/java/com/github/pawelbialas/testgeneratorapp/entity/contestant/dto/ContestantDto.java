package com.github.pawelbialas.testgeneratorapp.entity.contestant.dto;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.shared.BaseItem;
import lombok.*;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContestantDto extends BaseItem {

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

    private String contestantNumber;
    private List<SkillTestDto> skillTests = new ArrayList<>();
    private List<ResultDto> results = new ArrayList<>();


}
