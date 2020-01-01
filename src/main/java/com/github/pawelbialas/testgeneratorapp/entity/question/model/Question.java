package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.MainTech;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import com.github.pawelbialas.testgeneratorapp.shared.SkillLevel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Questions")
public class Question extends BaseEntity {

    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private MainTech mainTech;
    @Column(nullable = false)
    private String specificTech;
    @Column(nullable = false)
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question")
    private Set<Answer> answers = new HashSet<>();

    public Question(String contents, MainTech mainTech, String specificTech, HashSet<Answer> answers, SkillLevel skillLevel) {
        this.contents = contents;
        this.mainTech = mainTech;
        this.specificTech = specificTech;
        this.answers = answers;
        this.skillLevel = skillLevel;
    }

    public Question () {

    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public MainTech getMainTech() {
        return mainTech;
    }

    public void setMainTech(MainTech mainTech) {
        this.mainTech = mainTech;
    }

    public String getSpecificTech() {
        return specificTech;
    }

    public void setSpecificTech(String specificTech) {
        this.specificTech = specificTech;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }
}
