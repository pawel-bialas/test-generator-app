package com.github.pawelbialas.testgeneratorapp.entity.candidate.model;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class Candidate extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private String candidateNumber;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<SkillTest> skillTests = new ArrayList<>();
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Result> results = new ArrayList<>();


    public Candidate (UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                      String candidateNumber, ArrayList<SkillTest> skillTests, ArrayList<Result> results) {
        super(id, version, createdDate, lastModifiedDate);
        this.candidateNumber = candidateNumber;
        this.skillTests = skillTests;
        this.results = results;
    }

    public void addTest (SkillTest skillTest) {
        skillTests.add(skillTest);
        skillTest.setCandidate(this);
    }

    public void removeTest (SkillTest skillTest) {
        skillTests.remove(skillTest);
        skillTest.setCandidate(null);
    }

    public void addResult (Result result) {
        results.add(result);
        result.setCandidate(this);
    }

    public void removeResult (Result result) {
        results.remove(result);
        result.setCandidate(null);
    }

}
