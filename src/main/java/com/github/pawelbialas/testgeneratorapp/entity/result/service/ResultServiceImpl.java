package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import com.github.pawelbialas.testgeneratorapp.shared.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultServiceImpl implements ResultService {


    private final ResultRepository resultRepository;
    private final SkillTestServiceImpl skillTestService;
    private final ResultMapper mapper;
    private final ContestantServiceImpl contestantService;

    public ResultServiceImpl(ResultRepository resultRepository,
                             SkillTestServiceImpl skillTestService,
                             ResultMapper mapper,
                             ContestantServiceImpl contestantService) {
        this.resultRepository = resultRepository;
        this.skillTestService = skillTestService;
        this.mapper = mapper;
        this.contestantService = contestantService;
    }


    public Float resolveTest(UUID contestantUUID, UUID baseTestId, SkillTestDto resultTest) {
        Integer maxScore = 0;
        Integer contestantScore = 0;

        SkillTestDto baseTest = skillTestService.findTestByUUID(baseTestId);

        if (baseTest != null) {
            maxScore = calculateMaxScore(baseTest);
            contestantScore = checkAnswers(baseTest, resultTest);
        } else throw new NotFoundException("ResultService: Base test not found. Matching the result is impossible");




        return ((float) contestantScore / (float) maxScore) * 100;
    }

    public Integer calculateMaxScore(SkillTestDto baseTest) {
        Integer maxScore = 0;
        List<QuestionDto> questions = baseTest.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            List<AnswerDto> answers = questions.get(i).getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                if (answers.get(j).getCorrect().equals(true)) {
                    maxScore++;
                }
            }
        }
        return maxScore;
    }


    private Integer checkAnswers(SkillTestDto baseTest, SkillTestDto resultTest) {
        List<QuestionDto> baseQuestions = baseTest.getQuestions();
        List<QuestionDto> resultQuestions = resultTest.getQuestions();
        boolean integrity = questionIntegrityValidator(baseQuestions, resultQuestions);
        if (!integrity) {
            throw new BadRequestException("ResultService: test integrity error");
        }
        return calculateFinalScore(baseQuestions, resultQuestions);
    }

    private Integer calculateFinalScore(List<QuestionDto> baseQuestions, List<QuestionDto> resultQuestions) {
        Integer score = 0;
        for (int i = 0; i < baseQuestions.size(); i++) {
            List<AnswerDto> baseAnswers = baseQuestions.get(i).getAnswers();
            List<AnswerDto> resultAnswers = resultQuestions.get(i).getAnswers();
            for (int j = 0; j < baseAnswers.size(); j++) {
                if (baseAnswers.get(j).getCorrect().equals(false)) {
                    break;
                } else if (resultAnswers.get(j).getCorrect().equals(true)) {
                    score++;
                }
            }
        }
        return score;
    }

    private boolean questionIntegrityValidator(List<QuestionDto> baseQuestions, List<QuestionDto> resultQuestions) {
        boolean integrityValidator = true;
        if (baseQuestions.size() != resultQuestions.size()) {
            return integrityValidator = false;
        }
        for (int i = 0; i < baseQuestions.size(); i++) {
            if (!baseQuestions.get(i).getContents().equals(resultQuestions.get(i).getContents())) {
                integrityValidator = false;
                break;
            }
            if (!answerIntegrityValidation(baseQuestions.get(i).getAnswers(), resultQuestions.get(i).getAnswers())) {
                integrityValidator = false;
                break;
            }
        }
        return integrityValidator;
    }

    private boolean answerIntegrityValidation(List<AnswerDto> baseAnswers, List<AnswerDto> resultAnswers) {
        boolean integrityValidator = true;
        if (baseAnswers.size() != resultAnswers.size()) {
            return integrityValidator = false;
        }
        for (int i = 0; i < baseAnswers.size(); i++) {
            if (!baseAnswers.get(i).getAnswer().equals(resultAnswers.get(i).getAnswer())) {
                integrityValidator = false;
                break;
            }
        }
        return integrityValidator;
    }

}
