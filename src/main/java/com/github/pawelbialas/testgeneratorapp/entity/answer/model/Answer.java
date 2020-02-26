package com.github.pawelbialas.testgeneratorapp.entity.answer.model;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@Table(name = "answers")
@NoArgsConstructor
@Entity
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String answer;
    @Column(nullable = false)
    private Boolean correct;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @Builder
    public Answer (UUID id,
                   Long version,
                   Timestamp createdDate,
                   Timestamp lastModifiedDate,
                   String answer,
                   Boolean correct,
                   Question question) {
        super(id, version, createdDate, lastModifiedDate);
        this.answer = answer;
        this.correct = correct;
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", correct=" + correct +
                ", question=" + question.getId() +
                '}';
    }
}
