package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.answer.service.AnswerService;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public BootstrapSomeData(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Answer answer1 = new Answer();
        Answer answer2 = new Answer();
        Answer answer3 = new Answer();

        answer1.setAnswer("aaa");
        answer2.setAnswer("bbb");
        answer3.setAnswer("ccc");

        answer1.setCorrect(true);
        answer2.setCorrect(false);
        answer3.setCorrect(false);

        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();

        UUID id = question1.getId();

        HashSet<Answer> answers = new HashSet<>();

        question1.setSpecificTech("aaa");
        question2.setSpecificTech("bbb");
        question3.setSpecificTech("ccc");

        question1.setSkillLevel(SkillLevel.JUNIOR);
        question2.setSkillLevel(SkillLevel.SENIOR);
        question3.setSkillLevel(SkillLevel.MID);

        question1.setContents("aaa");
        question2.setContents("bbb");
        question3.setContents("ccc");

        question1.setMainTech(MainTech.JAVA);
        question2.setMainTech(MainTech.JAVA);
        question3.setMainTech(MainTech.JAVA);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        answer1.setQuestion(question1);
        answer2.setQuestion(question2);
        answer3.setQuestion(question3);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        List<Question> allQuestions = questionRepository.findAll();
        for (Question question: allQuestions) {
            System.out.println(question.getId());
        }

        Question bbb = questionRepository.findByContentsEquals("bbb");
        System.out.println(bbb);

    }

}
