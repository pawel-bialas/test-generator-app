package com.github.pawelbialas.testgeneratorapp.entity.result.dto;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResultDto extends BaseItem {


    private Float score;
    private SkillTestDto skillTest;
    private ContestantDto contestant;

    @Builder
    public ResultDto(UUID id,
                     Long version,
                     OffsetDateTime createdDate,
                     OffsetDateTime lastModifiedDate,
                     Float score,
                     SkillTestDto skillTest,
                     ContestantDto contestant
    ) {
        super(id, version, createdDate, lastModifiedDate);
        this.score = score;
        this.skillTest = skillTest;
        this.contestant = contestant;
    }


    @Override
    public String toString() {
        return "ResultDto{" +
                "id='" + this.getId() + '\'' +
                "score=" + score +
                ", skillTest=" + skillTest.getId()+
                ", contestant=" + contestant.getId()+
                '}';
    }
}
