package com.github.pawelbialas.testgeneratorapp.entity.test.model;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@ToString
@Entity
public class SkillTest extends BaseEntity {


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "skill_test_questions",
            joinColumns = @JoinColumn(name = "skill_test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions = new HashSet<>();
    @ManyToOne (fetch = FetchType.LAZY)
    private Candidate candidate;
    @OneToOne
    @JoinColumn(name = "result_id")
    private Result result;


    public SkillTest () {

    }

    public SkillTest(HashSet<Question> questions, Candidate candidate, Result result) {
        this.questions = questions;
        this.candidate = candidate;
        this.result = result;

    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
