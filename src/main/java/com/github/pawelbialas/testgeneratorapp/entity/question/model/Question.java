package com.github.pawelbialas.testgeneratorapp.entity.question.model;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
@Entity
public class Question extends BaseEntity {

    private java.lang.String contents;
    private String mainTech;
    private java.lang.String specificTech;
    @Enumerated
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Question(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                    java.lang.String contents, String mainTech, java.lang.String specificTech, ArrayList<Answer> answers, SkillLevel skillLevel) {
        super(id, version, createdDate, lastModifiedDate);
        this.contents = contents;
        this.mainTech = mainTech;
        this.specificTech = specificTech;
        this.answers = answers;
        this.skillLevel = skillLevel;
    }

    public void addAnswer(Answer answer) {
        this.getAnswers().add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer (Answer answer) {
        this.getAnswers().remove(answer);
        answer.setQuestion(null);
    }

    @Override
    public java.lang.String toString() {
        return "Question{" +
                "id='" + this.getId() + '\'' +
                "contents='" + contents + '\'' +
                ", mainTech=" + mainTech +
                ", specificTech='" + specificTech + '\'' +
                ", skillLevel=" + skillLevel +
                ", answers=" + answers.size() +
                '}';
    }
}
