package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.shared.BaseItem;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnswerDto extends BaseItem {

    private String answer;
    private Boolean correct;
    private QuestionDto question;

    @Builder
    public AnswerDto (UUID id,
                      Long version,
                      OffsetDateTime createdDate,
                      OffsetDateTime lastModifiedDate,
                      String answer,
                      Boolean correct,
                      QuestionDto question) {
        super(id, version, createdDate, lastModifiedDate);
        this.answer = answer;
        this.correct = correct;
        this.question = question;

    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "answer='" + answer + '\'' +
                ", correct=" + correct +
                ", question_id=" + question +
                '}';
    }
}
