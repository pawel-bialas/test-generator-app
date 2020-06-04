package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class QuestionDto extends BaseItem {

    private java.lang.String contents;
    private String mainTech;
    private java.lang.String specificTech;
    private SkillLevel skillLevel;
    private Set<AnswerDto> answers = new LinkedHashSet<>();

    @Builder
    public QuestionDto (UUID id,
                        Long version,
                        OffsetDateTime createdDate,
                        OffsetDateTime lastModifiedDate,
                        String contents,
                        String mainTech,
                        String specificTech,
                        LinkedHashSet<AnswerDto> answers,
                        SkillLevel skillLevel) {
        super(id, version, createdDate, lastModifiedDate);
        this.contents = contents;
        this.mainTech = mainTech;
        this.specificTech = specificTech;
        this.answers = answers;
        this.skillLevel = skillLevel;
    }

    public void addAnswer(AnswerDto answerDto) {
        answers.add(answerDto);
        answerDto.setQuestion(this);
    }

    public void removeAnswer (AnswerDto answerDto) {
        answers.remove(answerDto);
        answerDto.setQuestion(null);
    }


    @Override
    public java.lang.String toString() {
        return "QuestionDto{" +
                "id='" + this.getId() + '\'' +
                "contents='" + contents + '\'' +
                ", mainTech=" + mainTech +
                ", specificTech='" + specificTech + '\'' +
                ", skillLevel=" + skillLevel +
                ", answers=" + answers.size() +
                '}';
    }
}
