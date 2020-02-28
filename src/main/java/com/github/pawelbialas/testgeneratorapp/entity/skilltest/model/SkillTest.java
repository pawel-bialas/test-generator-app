package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@Table(name = "skill_tests")
@NoArgsConstructor
@Entity
public class SkillTest extends BaseEntity {


    @OneToMany
    private List<Question> questions = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Contestant contestant;
    @OneToOne(mappedBy = "skillTest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Result result;
    @Enumerated
    private TestStatus testStatus;

    @Builder
    public SkillTest(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                     ArrayList<Question> questions, Contestant contestant, Result result) {
        super(id, version, createdDate, lastModifiedDate);
        this.questions = questions;
        this.contestant = contestant;
        this.result = result;
    }

    @Override
    public String toString() {
        return "SkillTest{" +
                "questions=" + questions +
                ", contestant=" + contestant +
                ", result=" + result +
                '}';
    }
}
