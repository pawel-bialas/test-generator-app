package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillTestServiceImpl implements SkillTestService {

    @Value("${regular.test.size}")
    private Integer regularTestSize;

    private final QuestionServiceImpl questionServiceImpl;
    private final SkillTestRepository skillTestRepository;
    private final ContestantServiceImpl contestantServiceImpl;
    private final ResultServiceImpl resultServiceImpl;
    private final SkillTestMapper testMapper;


    public SkillTestServiceImpl(
            QuestionServiceImpl questionServiceImpl,
            SkillTestRepository skillTestRepository,
            ContestantServiceImpl contestantServiceImpl,
            ResultServiceImpl resultServiceImpl,
            SkillTestMapper testMapper
    ) {
        this.questionServiceImpl = questionServiceImpl;
        this.contestantServiceImpl = contestantServiceImpl;
        this.skillTestRepository = skillTestRepository;
        this.resultServiceImpl = resultServiceImpl;
        this.testMapper = testMapper;
    }

    public SkillTestDto findTestByUUID(UUID testUUID) {
        try {
            return skillTestRepository.findById(testUUID)
                    .map(skillTest -> testMapper.objectToDto(skillTest))
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    notFound.getMessage()
            );
        }
    }

    public List<SkillTestDto> findTestByContestantNumber(String contestantNumber) {
        try {
            if (contestantServiceImpl.confirmContestant(contestantNumber)) {
                return skillTestRepository.findByContestant_ContestantNumber(contestantNumber)
                        .stream()
                        .map(skillTest -> testMapper.objectToDto(skillTest))
                        .collect(Collectors.toList());
            } else throw new EntityNotFoundException("message name to change");
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    notFound.getMessage()
            );
        }
    }

    public SkillTestDto createNewTest(String contestantNumber, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        try {
            if (contestantNumber == null || skillLevel == null || mainTech == null) {
                throw new IllegalArgumentException("message name to change");
            }
            ContestantDto contestant = contestantServiceImpl.findContestantByNumber(contestantNumber);
            if (contestant == null) {
                contestant = ContestantDto.builder()
                        .contestantNumber(contestantNumber)
                        .results(new ArrayList<>())
                        .skillTests(new ArrayList<>())
                        .build();
                contestantServiceImpl.saveOrUpdate(contestant);
            }
            SkillTestDto skillTest = generateTest(contestant, mainTech, skillLevel, isRegularTest);
            skillTestRepository.save(testMapper.dtoToObject(skillTest));
            return skillTest;
        } catch (IllegalArgumentException illegalArgument) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    illegalArgument.getMessage()
            );
        }
    }

    private SkillTestDto generateTest(ContestantDto contestant, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest) {
        SkillTestDto result = SkillTestDto.builder()
                .contestant(contestant)
                .build();

        int[] codeQuestionPattern = {3, 5, 7, 9, 10};

        HashSet<QuestionDto> questions = new HashSet<>(questionServiceImpl.findAllByMainTechAndSkillLevel(mainTech, skillLevel));

        questions.stream()
                .filter(question -> !question.getSpecificTech().equals("Code"))
                .limit(regularTestSize - 5)
                .forEachOrdered(result.getQuestions()::add);

        List<QuestionDto> codeQuestions = questionServiceImpl.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, "Code", skillLevel);
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
