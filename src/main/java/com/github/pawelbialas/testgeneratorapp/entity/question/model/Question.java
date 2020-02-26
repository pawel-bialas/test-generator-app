package com.github.pawelbialas.testgeneratorapp.entity.question.model;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.*;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Table(name = "questions")
@Entity
public class Question extends BaseEntity {

    private String contents;
    @Enumerated
    private MainTech mainTech;
    private String specificTech;
    @Enumerated
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

    public Question () {

    }

    @Builder
    public Question(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                    String contents, MainTech mainTech, String specificTech, ArrayList<Answer> answers, SkillLevel skillLevel) {
        super(id, version, createdDate, lastModifiedDate);
        this.contents = contents;
        this.mainTech = mainTech;
        this.specificTech = specificTech;
        this.answers = answers;
        this.skillLevel = skillLevel;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer (Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    @Override
    public String toString() {
        return "Question{" +
                "contents='" + contents + '\'' +
                ", mainTech=" + mainTech +
                ", specificTech='" + specificTech + '\'' +
                ", skillLevel=" + skillLevel +
                ", answers=" + answers +
                '}';
    }
}
