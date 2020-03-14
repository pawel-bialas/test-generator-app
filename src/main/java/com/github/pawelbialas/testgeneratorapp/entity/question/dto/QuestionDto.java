package com.github.pawelbialas.testgeneratorapp.entity.question.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
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
public class QuestionDto extends BaseItem {

    private String contents;
    private MainTech mainTech;
    private String specificTech;
    private SkillLevel skillLevel;
    private List<AnswerDto> answers = new ArrayList<>();

    @Builder
    public QuestionDto (UUID id,
                        Long version,
                        OffsetDateTime createdDate,
                        OffsetDateTime lastModifiedDate,
                        String contents,
                        MainTech mainTech,
                        String specificTech,
                        ArrayList<AnswerDto> answers,
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
    public String toString() {
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
