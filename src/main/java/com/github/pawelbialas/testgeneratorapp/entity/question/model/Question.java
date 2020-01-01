package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.technologies.MainTech;
import com.github.pawelbialas.testgeneratorapp.utils.BaseEntity;

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
    @OneToMany(mappedBy = "question")
    private Set<Answer> answers = new HashSet<>();

    public Question(String contents, MainTech mainTech, String specificTech, HashSet<Answer> answers) {
        this.contents = contents;
        this.mainTech = mainTech;
        this.specificTech = specificTech;
        this.answers = answers;
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
}
