package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.test.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@ToString
public class Question extends BaseEntity {

    private String contents;
    @Enumerated
    private MainTech mainTech;
    private String specificTech;
    @Enumerated
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();
    @ManyToMany (mappedBy = "questions")
    private Set<SkillTest> tests = new HashSet<>();

    public Question(String contents, MainTech mainTech, String specificTech, ArrayList<Answer> answers, SkillLevel skillLevel) {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer (Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}
