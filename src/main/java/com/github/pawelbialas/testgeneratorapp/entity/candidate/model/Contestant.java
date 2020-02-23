package com.github.pawelbialas.testgeneratorapp.entity.candidate.model;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Contestant extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private String candidateNumber;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<SkillTest> skillTests = new ArrayList<>();
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Result> results = new ArrayList<>();

    @Builder
    public Contestant(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                      String candidateNumber, ArrayList<SkillTest> skillTests, ArrayList<Result> results) {
        super(id, version, createdDate, lastModifiedDate);
        this.candidateNumber = candidateNumber;
        this.skillTests = skillTests;
        this.results = results;
    }

    public void addTest (SkillTest skillTest) {
        skillTests.add(skillTest);
        skillTest.setContestant(this);
    }

    public void removeTest (SkillTest skillTest) {
        skillTests.remove(skillTest);
        skillTest.setContestant(null);
    }

    public void addResult (Result result) {
        results.add(result);
        result.setContestant(this);
    }

    public void removeResult (Result result) {
        results.remove(result);
        result.setContestant(null);
    }

}
