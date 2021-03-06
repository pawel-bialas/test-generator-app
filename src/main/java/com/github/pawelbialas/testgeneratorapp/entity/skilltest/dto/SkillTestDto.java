package com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SkillTestDto extends BaseItem {


    private List<QuestionDto> questions = new ArrayList<>();
    private ContestantDto contestant;
    private ResultDto result;
    private String testStatus;

    @Builder
    public SkillTestDto (UUID id,
                         Long version,
                         OffsetDateTime createdDate,
                         OffsetDateTime lastModifiedDate,
                         ArrayList<QuestionDto> questions,
                         ContestantDto contestant,
                         ResultDto result,
                         String testStatus) {
        super(id, version, createdDate, lastModifiedDate);
        this.questions = questions;
        this.contestant = contestant;
        this.result = result;
        this.testStatus = testStatus;
    }

    @Override
    public String toString() {
        return "SkillTestDto{" +
                " uuid=" + this.getId() +
                ", questions=" + questions +
                ", contestant=" + contestant +
                ", result=" + result +
                ", testStatus='" + testStatus + '\'' +
                '}';
    }
}
