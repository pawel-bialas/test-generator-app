package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.ContestantRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import com.github.pawelbialas.testgeneratorapp.entity.test.service.SkillTestService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SkillTestService skillTestService;
    private final ContestantRepository contestantRepository;

    public BootstrapSomeData(QuestionService questionService, QuestionRepository questionRepository, AnswerRepository answerRepository, SkillTestService skillTestService, ContestantRepository contestantRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.skillTestService = skillTestService;
        this.contestantRepository = contestantRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {

//        questionService.readQuestionsFromCsv("aaa");
//
//        List<Question> all = questionService.findAll();
//
//        all.forEach(System.out::println);
//
//        System.out.println("======================");
//
//        List<Question> allByMainTechAndSkillLevel = questionService.findAllByMainTechAndSkillLevel(MainTech.JAVA, SkillLevel.JUNIOR);
//
//        allByMainTechAndSkillLevel.forEach(System.out::println);

//
//        Candidate candidate = new Candidate();
//        candidate.setCandidateNumber(1234234L);
//        candidate.setResults(null);
//        candidate.setSkillTests(null);
//
//        skillTestService.generateTest(candidate, MainTech.JAVA, SkillLevel.MID, true);


    }
}




