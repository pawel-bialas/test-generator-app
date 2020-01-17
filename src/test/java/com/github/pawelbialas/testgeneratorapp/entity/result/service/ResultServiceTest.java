package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultServiceTest {




    @Test
    public void shouldReturn4WhenCalculatingMaxScore() {
        // Given
        ResultService resultService = new ResultService();
        SkillTest skillTest = new SkillTest();

        Question question1 = new Question();
        Question question2 = new Question();
        Answer answer1 = new Answer();
        answer1.setCorrect(false);
        Answer answer2 = new Answer();
        answer2.setCorrect(true);
        Answer answer3 = new Answer();
        answer3.setCorrect(true);

        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        question1.setAnswers(answers);
        question2.setAnswers(answers);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        skillTest.setQuestions(questions);

        // When
        Integer result = resultService.calculateMaxScore(skillTest);
        System.out.println(result);
        // Then

    }

}