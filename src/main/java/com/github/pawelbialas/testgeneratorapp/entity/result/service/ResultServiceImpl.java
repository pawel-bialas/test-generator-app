package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultMapper;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider;
import com.github.pawelbialas.testgeneratorapp.shared.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class ResultServiceImpl implements ResultService {


    private final ResultRepository repository;
    private final SkillTestServiceImpl skillTestService;
    private final ResultMapper mapper;
    private final ContestantServiceImpl contestantService;

    public ResultServiceImpl(ResultRepository repository,
                             SkillTestServiceImpl skillTestService,
                             ResultMapper mapper,
                             ContestantServiceImpl contestantService) {
        this.repository = repository;
        this.skillTestService = skillTestService;
        this.mapper = mapper;
        this.contestantService = contestantService;
    }


    @Override
    public ResultDto saveOrUpdate(ResultDto resultDto) {
        Result save = repository.save(mapper.dtoToObject(resultDto, CycleAvoidingMappingContextProvider.contextProvider()));
        return mapper.objectToDto(save, contextProvider());
    }

    @Override
    public Optional<ResultDto> findById(UUID uuid) {
        return Optional.of(mapper.objectToDto(repository.getOne(uuid), contextProvider()));

    }

    @Override

    public List<ResultDto> findAll() {
        return repository.findAll().stream()
                .map(val -> mapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> findAllByContestantIdOrContestantNumber(UUID contestantId, String contestantNumber) {
        return repository.findAllByContestant_IdOrContestant_ContestantNumber(contestantId, contestantNumber).stream()
                .map(val -> mapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }

    public Float resolveTest(UUID contestantUUID, UUID baseTestId, SkillTestDto resultTest) {
        if (contestantUUID == null || baseTestId == null || resultTest == null) {
            throw new NotFoundException("ResultService: Missing data. Matching the result is impossible");
        }
        Integer maxScore = 0;
        Integer contestantScore = 0;

        Optional<SkillTestDto> baseTest = skillTestService.findTestByUUID(baseTestId);
        if (baseTest.isEmpty()) {
            throw new NotFoundException("ResultService: BaseTest not found");
        }

        Optional<ContestantDto> contestant = contestantService.findById(contestantUUID);
        if (contestant.isEmpty()) {
            throw new NotFoundException("ResultService: Contestant not found");
        }
        maxScore = calculateMaxScore(baseTest.get());
        contestantScore = checkAnswers(baseTest.get(), resultTest);

        //TODO return type to change -> needed repository to store
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
