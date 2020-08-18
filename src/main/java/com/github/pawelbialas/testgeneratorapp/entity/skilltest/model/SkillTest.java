package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkillTest extends BaseEntity {

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.LAZY)
    @JoinTable(name = "skill_est_suestions",
            joinColumns = @JoinColumn(name = "skill_test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();
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
