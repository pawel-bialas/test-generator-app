package com.github.pawelbialas.testgeneratorapp.entity.test.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.test.repository.SkillTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SkillTestService {


    @Autowired private QuestionService questionService;
    @Autowired private SkillTestRepository skillTestRepository;
    @Value("${regular.test.size}")
    private Integer testSize;


    public void generateTest (Candidate candidate, MainTech mainTech, SkillLevel skillLevel) {
        List<Question> mainTechQuestions = questionService.findAllByMainTech(mainTech);


    }
}
