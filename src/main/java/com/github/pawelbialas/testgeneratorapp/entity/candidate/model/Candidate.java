package com.github.pawelbialas.testgeneratorapp.entity.candidate.model;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
public class Candidate extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private Long candidateNumber;
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<SkillTest> skillTests = new ArrayList<>();
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Result> results = new ArrayList<>();

    public Candidate() {
    }

    public Candidate (Long candidateNumber, ArrayList<SkillTest> skillTests, ArrayList<Result> results) {
        this.candidateNumber = candidateNumber;
        this.skillTests = skillTests;
        this.results = results;
    }

    public Long getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(Long candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    public List<SkillTest> getSkillTests() {
        return skillTests;
    }

    public void setSkillTests(List<SkillTest> skillTests) {
        this.skillTests = skillTests;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
