package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.MainTech;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import com.github.pawelbialas.testgeneratorapp.shared.SkillLevel;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@ToString
public class Question extends BaseEntity {

    private String contents;
    private MainTech mainTech;
    private String specificTech;
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Answer> answers;

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
