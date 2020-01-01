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
import java.util.Set;
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

        questionRepository.saveAndFlush(question1);
        questionRepository.saveAndFlush(question2);
        questionRepository.saveAndFlush(question3);

        answer1.setQuestion(question1);
        answer2.setQuestion(question2);
        answer3.setQuestion(question3);

        answerRepository.saveAndFlush(answer1);
        answerRepository.saveAndFlush(answer2);
        answerRepository.saveAndFlush(answer3);

        List<Question> allQuestions = questionRepository.findAll();
        for (Question question: allQuestions) {
            System.out.println(question.getId());
        }

        Question bbb = questionRepository.findByContentsEquals("bbb");
        System.out.println(bbb);

        Set<Question> allByMainTech = questionRepository.findAllByMainTech(MainTech.JAVA);
        System.out.println(allByMainTech);

        Set<Question> allByMainTechAndSkillLevel = questionRepository.findAllByMainTechAndSkillLevel(MainTech.JAVA, SkillLevel.SENIOR);
        System.out.println(allByMainTechAndSkillLevel);

        List<Answer> all = answerRepository.findAll();
        System.out.println(all);
        Question aaa1 = questionRepository.findByContentsEquals("aaa");
        Answer aaa = answerRepository.findByAnswer("aaa");
        answers.add(aaa);
        aaa1.setAnswers(answers);
        System.out.println(aaa1);
        questionRepository.save(aaa1);



    }

}
