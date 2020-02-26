package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantService;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultService;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
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

    @Value("${regular.test.size}")
    private Integer regularTestSize;

    private final QuestionService questionService;
    private final SkillTestRepository skillTestRepository;
    private final ContestantService contestantService;
    private final ResultService resultService;

    @Autowired
    public SkillTestService(QuestionService questionService, SkillTestRepository skillTestRepository, ContestantService contestantService, ResultService resultService) {
        this.questionService = questionService;
        this.skillTestRepository = skillTestRepository;
        this.contestantService = contestantService;
        this.resultService = resultService;
    }


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

    public List<SkillTest> findTestByContestantNumber(String contestantNumber) {
        try {
            if (!contestantService.confirmCandidate(contestantNumber)) {
                throw new EntityNotFoundException("message name to change");
            }
            return skillTestRepository.findByContestant_ContestantNumber(contestantNumber);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    notFound.getMessage()
            );
        }
    }

    public SkillTest createNewTest(String contestantNumber, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        try {
            if (contestantNumber == null || skillLevel == null || mainTech == null) {
                throw new IllegalArgumentException("message name to change");
            }
            Contestant contestant = contestantService.findCandidateByNumber(contestantNumber);
            if (contestant == null) {
                 contestant = Contestant.builder()
                        .contestantNumber(contestantNumber)
                        .results(new ArrayList<Result>())
                        .skillTests(new ArrayList<SkillTest>())
                        .build();
                contestant = contestantService.saveOrUpdate(contestant);
            }
            SkillTest skillTest = generateTest(contestant, mainTech, skillLevel, isRegularTest);
            skillTestRepository.save(skillTest);
            return skillTest;
        } catch (IllegalArgumentException illegalArgument) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    illegalArgument.getMessage()
            );
        }
    }

    private SkillTest generateTest(Contestant contestant, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        SkillTest result = SkillTest.builder()
                .contestant(contestant)
                .build();
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
