package com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class SkillTestDto extends BaseItem {


    private Set<QuestionDto> questions = new LinkedHashSet<>();
    private ContestantDto contestant;
    private ResultDto result;
    private String testStatus;

    @Builder
    public SkillTestDto (UUID id,
                         Long version,
                         OffsetDateTime createdDate,
                         OffsetDateTime lastModifiedDate,
                         LinkedHashSet<QuestionDto> questions,
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
                "id='" + this.getId() + '\'' +
                "questions=" + questions.size() +
                ", contestant=" + contestant.getId() +
                ", result=" + result.getId() +
                ", testStatus='" + testStatus + '\'' +
                '}';
    }
}
