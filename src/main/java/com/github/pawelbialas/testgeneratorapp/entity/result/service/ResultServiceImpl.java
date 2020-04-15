package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultServiceImpl implements ResultService {


    private final ResultRepository resultRepository;
    private final SkillTestRepository skillTestRepository;
    private final ResultMapper mapper;

    public ResultServiceImpl(ResultRepository resultRepository, SkillTestRepository skillTestRepository, ResultMapper mapper) {
        this.resultRepository = resultRepository;
        this.skillTestRepository = skillTestRepository;
        this.mapper = mapper;
    }


    public Float resolveTest(UUID candidateId, UUID baseTestId, SkillTest resultTest) {
        SkillTest baseTest = null;
        Integer maxScore = 0;
        Integer contestantScore = 0;

            Optional<SkillTest> searchResult = skillTestRepository.findById(baseTestId);

            if (searchResult.isPresent()) {
                baseTest = searchResult.get();
                maxScore = calculateMaxScore(baseTest);
                contestantScore = checkAnswers(baseTest, resultTest);
            } else throw new NotFoundException("ResultService: Base test not found. Matching the result is impossible");

        return ((float) contestantScore / (float) maxScore) * 100;
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


     private Integer checkAnswers(SkillTest baseTest, SkillTest resultTest) {
            List<Question> baseQuestions = baseTest.getQuestions();
            List<Question> resultQuestions = resultTest.getQuestions();
            boolean integrity = questionIntegrityValidator(baseQuestions, resultQuestions);
            if (!integrity) {
                throw new BadRequestException("ResultService: test integrity error");
            }
            return calculateFinalScore(baseQuestions, resultQuestions);
        }

     private Integer calculateFinalScore(List<Question> baseQuestions, List<Question> resultQuestions) {
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
