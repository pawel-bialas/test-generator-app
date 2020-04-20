package com.github.pawelbialas.testgeneratorapp.entity.contestant.model;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contestants")
@Entity
public class UUID extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private String contestantNumber;
    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL)
    private List<SkillTest> skillTests = new ArrayList<>();
    @OneToMany(mappedBy = "contestantId", cascade = CascadeType.ALL)
    private List<Result> results = new ArrayList<>();


    @Builder
    public UUID(java.util.UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                String contestantNumber, ArrayList<SkillTest> skillTests, ArrayList<Result> results) {
        super(id, version, createdDate, lastModifiedDate);
        this.contestantNumber = contestantNumber;
        this.skillTests = skillTests;
        this.results = results;
    }

    public void addTest (SkillTest skillTest) {
        this.getSkillTests().add(skillTest);
        skillTest.setContestant(this);
    }

    public void removeTest (SkillTest skillTest) {
        this.getSkillTests().remove(skillTest);
        skillTest.setContestant(null);
    }

    public void addResult (Result result) {
        this.getResults().add(result);
        result.setContestant(this);
    }

    public void removeResult (Result result) {
        this.getResults().remove(result);
        result.setContestant(null);
    }

    @Override
    public String toString() {
        return "Contestant{" +
                "id='" + this.getId() + '\'' +
                "contestantNumber='" + contestantNumber + '\'' +
                ", skillTests=" + skillTests.size() +
                ", results=" + results.size() +
                '}';
    }
}
