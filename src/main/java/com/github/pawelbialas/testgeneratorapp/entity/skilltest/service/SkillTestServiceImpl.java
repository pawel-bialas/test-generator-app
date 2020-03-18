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
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.Skilltest.SkillTestNotFound;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.Skilltest.SkillTestServiceBadRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
            SkillTestMapper testMapper) {
        this.questionServiceImpl = questionServiceImpl;
        this.contestantServiceImpl = contestantServiceImpl;
        this.skillTestRepository = skillTestRepository;
        this.resultServiceImpl = resultServiceImpl;
        this.testMapper = testMapper;
    }

    public SkillTestDto findTestByUUID(UUID testUUID) {
        return skillTestRepository.findById(testUUID)
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new SkillTestNotFound("SkillTestService: no test with given Id"));
    }

    public List<SkillTestDto> findTestByContestantNumber(String contestantNumber) {

        return skillTestRepository.findByContestant_ContestantNumber(contestantNumber)
                .stream()
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());

    }

    public SkillTestDto createNewTest(String contestantNumber,
                                      MainTech mainTech,
                                      SkillLevel skillLevel,
                                      Boolean isRegularTest,
                                      Boolean addCodeBlocks) {
        if (contestantNumber == null || skillLevel == null || mainTech == null) {
            throw new SkillTestServiceBadRequest("SkillTestService: contestantNumber, SkillLevel or MainTech can't be null");
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
        SkillTestDto skillTest = generateTest(contestant, mainTech, skillLevel, isRegularTest, addCodeBlocks);
        skillTestRepository.save(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext()));
        return skillTest;
    }

    private SkillTestDto generateTest(ContestantDto contestant, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest, Boolean addCodeBlocks) {
        SkillTestDto result = SkillTestDto.builder()
                .contestant(contestant)
                .testStatus(String.valueOf(TestStatus.BASE))
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
