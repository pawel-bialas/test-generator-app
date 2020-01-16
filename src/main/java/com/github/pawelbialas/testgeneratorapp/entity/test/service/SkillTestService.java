package com.github.pawelbialas.testgeneratorapp.entity.test.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.service.CandidateService;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultService;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.test.repository.SkillTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class SkillTestService {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private SkillTestRepository skillTestRepository;
    @Value("${regular.test.size}")
    private Integer regularTestSize;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ResultService resultService;



    public SkillTest findTestByUUID(UUID testUUID) {
        try {
            Optional<SkillTest> test = skillTestRepository.findById(testUUID);
            if (test.isPresent()) {
                return test.get();
            } else throw new EntityNotFoundException("message name to change");
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    notFound.getMessage()
            );
        }
    }

    public List<SkillTest> findTestByCandidate(String candidateNumber) {
        try {
            if (!candidateService.confirmCandidate(candidateNumber)) {
                throw new EntityNotFoundException("message name to change");
            }
            return skillTestRepository.findByCandidate_CandidateNumber(candidateNumber);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    notFound.getMessage()
            );
        }
    }

    public SkillTest createNewTest(String candidateNumber, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        try {
            if (candidateNumber == null || skillLevel == null || mainTech == null) {
                throw new IllegalArgumentException("message name to change");
            }
            Candidate candidate = candidateService.findCandidateByNumber(candidateNumber);
            if (candidate == null) {
                candidate = new Candidate(candidateNumber, new ArrayList<>(), new ArrayList<>());
                candidate = candidateService.saveOrUpdate(candidate);
            }
            SkillTest skillTest = generateTest(candidate, mainTech, skillLevel, isRegularTest);
            skillTestRepository.save(skillTest);
            return skillTest;
        } catch (IllegalArgumentException illegalArgument) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    illegalArgument.getMessage()
            );
        }
    }

    private SkillTest generateTest(Candidate candidate, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        SkillTest result = new SkillTest(new ArrayList<Question>(), candidate, null);
        int[] codeQuestionPattern = {3, 5, 7, 9, 10};

        HashSet<Question> questions = new HashSet<>(questionService.findAllByMainTechAndSkillLevel(mainTech, skillLevel));

        questions.stream()
                .filter(question -> !question.getSpecificTech().equals("Code"))
                .limit(regularTestSize - 5)
                .forEachOrdered(result.getQuestions()::add);

        List<Question> codeQuestions = questionService.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, "Code", skillLevel);
        for (int i = 0; i < 5; i++) {
            result.getQuestions().add(codeQuestionPattern[i], codeQuestions.get(i));
        }
        return result;
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
