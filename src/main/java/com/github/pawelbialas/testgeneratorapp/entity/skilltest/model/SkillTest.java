package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SKILLTESTS")
@Entity
@NamedEntityGraphs(value = {
        @NamedEntityGraph(
                name = "skillTest.fullJoins",
                attributeNodes = {
                        @NamedAttributeNode(value = "questions", subgraph = "skillTest.questions.answers"),
                        @NamedAttributeNode(value = "contestant"),
                        @NamedAttributeNode(value = "result"),
                        @NamedAttributeNode(value = "testStatus"),
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "skillTest.questions.answers",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "answers")
                                }
                        )
                }
        )
})

public class SkillTest extends BaseEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Question> questions = new LinkedHashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Contestant contestant;
    @OneToOne(mappedBy = "skillTest", fetch = FetchType.LAZY)
    private Result result;
    @Enumerated(EnumType.STRING)
    private TestStatus testStatus;

    @Builder
    public SkillTest(UUID id,
                     Long version,
                     Timestamp createdDate,
                     Timestamp lastModifiedDate,
                     LinkedHashSet<Question> questions,
                     Contestant contestant,
                     Result result,
                     TestStatus testStatus) {
        super(id, version, createdDate, lastModifiedDate);
        this.questions = questions;
        this.contestant = contestant;
        this.result = result;
        this.testStatus = testStatus;
    }

    @Override
    public String toString() {
        return "SkillTest{" +
                "id='" + this.getId() + '\'' +
                "questions=" + questions.size() +
                ", contestant=" + contestant.getId() +
                ", result=" + result.getId() +
                ", testStatus=" + testStatus +
                '}';
    }
}
