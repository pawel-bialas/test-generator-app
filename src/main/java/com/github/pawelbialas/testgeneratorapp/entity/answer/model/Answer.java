package com.github.pawelbialas.testgeneratorapp.entity.answer.model;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.BaseEntity;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class Answer extends BaseEntity {

    @Column(nullable = false)
    String answer;
    @Column(nullable = false)
    Boolean correct;
    @ManyToOne (optional = false)
    @JoinColumn(name = "question_id")
    Question question;

// Ten układ w przypadku Question działa, Question jest nadrzędne

    public Answer () {

    }

    public Answer (String answer, Boolean correct, Question question) {
        this.answer = answer;
        this.correct = correct;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
