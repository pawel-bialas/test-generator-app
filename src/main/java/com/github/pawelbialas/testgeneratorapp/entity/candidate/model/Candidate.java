package com.github.pawelbialas.testgeneratorapp.entity.candidate.model;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.test.SkillTest;
import com.github.pawelbialas.testgeneratorapp.utils.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Candidate extends BaseEntity {

    @Column(updatable = false, nullable = false)
    private Long candidateNumber;
    @OneToMany(mappedBy = "candidate")
    private Set<SkillTest> skillTests = new HashSet<>();
    @OneToMany(mappedBy = "candidate")
    private Set<Result> results = new HashSet<>();

    public Candidate() {
    }

    public Candidate (Long candidateNumber, HashSet<SkillTest> skillTests, HashSet<Result> results) {
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

    public Set<SkillTest> getSkillTests() {
        return skillTests;
    }

    public void setSkillTests(Set<SkillTest> skillTests) {
        this.skillTests = skillTests;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }
}
