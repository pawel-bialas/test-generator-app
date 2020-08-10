package com.github.pawelbialas.testgeneratorapp.entity.answer.model;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ANSWERS")
@Entity
@NamedEntityGraphs(value = {
        @NamedEntityGraph(
                name = "answer.fullJoins",
                attributeNodes = {
                        @NamedAttributeNode(value = "answer"),
                        @NamedAttributeNode(value = "correct"),
                        @NamedAttributeNode(value = "question", subgraph = "question.anwers")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "question.anwers",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "answers")
                                }
                        )
                }
        )
})
public class Answer extends BaseEntity {

    @Column(nullable = false)
    private String answer;
    @Column(nullable = false)
    private Boolean correct;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Builder
    public Answer(UUID id,
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
                "id='" + this.getId() + '\'' +
                "answer='" + answer + '\'' +
                ", correct=" + correct +
                ", question=" + question.getId() +
                '}';
    }
}
