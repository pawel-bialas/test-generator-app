package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.test.service.SkillTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ResultService {


    private final ResultRepository resultRepository;
    private final SkillTestService skillTestService;

    public ResultService(ResultRepository resultRepository, SkillTestService skillTestService) {
        this.resultRepository = resultRepository;
        this.skillTestService = skillTestService;
    }


    public Double resolveTest(UUID candidateId, UUID baseTestId, SkillTest resultTest) {

        SkillTest baseTest = skillTestService.findTestByUUID(baseTestId);
        Integer score = checkAnswers(baseTest, resultTest);
        Integer maxScore = calculateMaxScore(baseTest);

        return 0D;

    }

    public Integer calculateMaxScore(SkillTest baseTest) {
        Integer maxScore = 0;
        List<Question> questions = baseTest.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            List<Answer> answers = questions.get(i).getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                if (answers.get(j).getCorrect().equals(true)) {
                    maxScore++;
                }
            }
        }
        return maxScore;
    }

    //do zmiany zwrot
    private Integer checkAnswers(SkillTest baseTest, SkillTest resultTest) {
        try {
            List<Question> baseQuestions = baseTest.getQuestions();
            List<Question> resultQuestions = resultTest.getQuestions();
            boolean integrity = questionIntegrityValidator(baseQuestions, resultQuestions);
            if (!integrity) {
                throw new DataIntegrityViolationException("message name to change");
            }
            return calculateFinalScore(baseQuestions, resultQuestions);

        } catch (DataIntegrityViolationException corruptedData) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    corruptedData.getMessage()
            );
        }


    }

    private Integer calculateFinalScore(List<Question> baseQuestions, List<Question> resultQuestions) {
        Integer score = 0;

        for (int i = 0; i < baseQuestions.size(); i++) {
            List<Answer> baseAnswers = baseQuestions.get(i).getAnswers();
            List<Answer> resultAnswers = resultQuestions.get(i).getAnswers();
            for (int j = 0; j < baseAnswers.size(); j++) {
                if (baseAnswers.get(j).getCorrect().equals(resultAnswers.get(j).getCorrect())) {
                    score++;
                }
            }
        }
        return score;
    }

    private boolean questionIntegrityValidator(List<Question> baseQuestions, List<Question> resultQuestions) {
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

    private boolean answerIntegrityValidation(List<Answer> baseAnswers, List<Answer> resultAnswers) {
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
