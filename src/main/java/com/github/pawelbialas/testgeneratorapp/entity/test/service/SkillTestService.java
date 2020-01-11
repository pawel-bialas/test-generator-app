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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillTestService {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private SkillTestRepository skillTestRepository;
    @Value("${regular.test.size}")
    private Integer regularTestSize;

    public void generateTest(Candidate candidate, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        SkillTest result = new SkillTest(new ArrayList<Question>(), candidate, null);
        int [] codeQuestionPattern = {3,5,7,9,10};

        HashSet<Question> questions = new HashSet<>(questionService.findAllByMainTechAndSkillLevel(mainTech, skillLevel));

        questions.stream()
                .filter(question -> !question.getSpecificTech().equals("Code"))
                .limit(regularTestSize - 5)
                .forEachOrdered(result.getQuestions()::add);

        List<Question> codeQuestions = questionService.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, "Code", skillLevel) ;
        for (int i = 0; i < 5; i++) {
            result.getQuestions().add(codeQuestionPattern[i],codeQuestions.get(i));
        }
        skillTestRepository.save(result);
    }

    private SkillLevel defineOtherSkillLevels(SkillLevel skillLevel) {
        SkillLevel result = null;
        switch (skillLevel) {
            case SENIOR:
            case JUNIOR:
                result = SkillLevel.MID;
                break;
            case MID:
                result = SkillLevel.SENIOR;
                break;
        }
        return result;
    }
}
