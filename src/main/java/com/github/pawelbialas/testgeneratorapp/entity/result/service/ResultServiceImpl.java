package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {


    @Autowired
    private  ResultRepository resultRepository;
    @Autowired
    private  SkillTestRepository skillTestRepository;
    @Autowired
    private  ResultMapper mapper;





    public Integer resolveTest(UUID candidateId, UUID baseTestId, SkillTest resultTest) {
        SkillTest baseTest = null;
        Integer maxScore = 0;
        Integer contestantScore = 0;
        try {

            Optional<SkillTest> searchResult = skillTestRepository.findById(baseTestId);

            if (searchResult.isPresent()) {
                baseTest = searchResult.get();
                maxScore = calculateMaxScore(baseTest);
                contestantScore = checkAnswers(baseTest, resultTest);
            } else throw new DataIntegrityViolationException("message name to change");
        } catch (DataIntegrityViolationException corruptedData) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    corruptedData.getMessage()
            );
        }

        return 0;
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


     Integer checkAnswers(SkillTest baseTest, SkillTest resultTest) {
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

     Integer calculateFinalScore(List<Question> baseQuestions, List<Question> resultQuestions) {
        Integer score = 0;

        for (int i = 0; i < baseQuestions.size(); i++) {
            List<Answer> baseAnswers = baseQuestions.get(i).getAnswers();
            List<Answer> resultAnswers = resultQuestions.get(i).getAnswers();
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

     boolean questionIntegrityValidator(List<Question> baseQuestions, List<Question> resultQuestions) {
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

     boolean answerIntegrityValidation(List<Answer> baseAnswers, List<Answer> resultAnswers) {
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
