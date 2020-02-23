package com.github.pawelbialas.testgeneratorapp.shared.bootstrap;

import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.CandidateRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import com.github.pawelbialas.testgeneratorapp.entity.test.service.SkillTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SkillTestService skillTestService;
    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

//        questionService.readQuestionsFromCsv();
//
//        Candidate candidate = new Candidate();
//        candidate.setCandidateNumber(1234234L);
//        candidate.setResults(null);
//        candidate.setSkillTests(null);
//
//        skillTestService.generateTest(candidate, MainTech.JAVA, SkillLevel.MID, true);


    }
}




