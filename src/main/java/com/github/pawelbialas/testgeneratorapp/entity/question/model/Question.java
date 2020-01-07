package com.github.pawelbialas.testgeneratorapp.entity.question.model;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
public class Question extends BaseEntity {

    private String contents;
    @Enumerated
    private MainTech mainTech;
    private String specificTech;
    @Enumerated
    private SkillLevel skillLevel;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

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

    @Override
    public String toString() {
        return "Question: "+ getId() +"{" +
                "contents='" + contents + '\'' +
                ", mainTech=" + mainTech +
                ", specificTech='" + specificTech + '\'' +
                ", skillLevel=" + skillLevel +
                ", answers=" + answers +
                '}';
    }
}
