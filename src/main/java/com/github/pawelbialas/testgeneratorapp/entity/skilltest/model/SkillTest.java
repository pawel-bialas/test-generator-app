package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "fullJoins", attributeNodes = {
                @NamedAttributeNode("questions"),
                @NamedAttributeNode("contestant"),
                @NamedAttributeNode("result")

        })
})
public class SkillTest extends BaseEntity {


    // MOD (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany
    private List<Question> questions = new ArrayList<>();
    @ManyToOne (fetch = FetchType.LAZY)
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
                     ArrayList<Question> questions,
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
