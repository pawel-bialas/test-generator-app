package com.github.pawelbialas.testgeneratorapp.entity.result.model;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class Result extends BaseEntity {

    @Column(nullable = false)
    private Integer score;
    @OneToOne
    @JoinColumn(name = "skill_test_id", nullable = false)
    private SkillTest skillTest;
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    public Result() {
    }

    public Result (Integer score, SkillTest skillTest, Candidate candidate) {
        this.score = score;
        this.skillTest = skillTest;
        this.candidate = candidate;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public SkillTest getSkillTest() {
        return skillTest;
    }

    public void setSkillTest(SkillTest skillTest) {
        this.skillTest = skillTest;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
