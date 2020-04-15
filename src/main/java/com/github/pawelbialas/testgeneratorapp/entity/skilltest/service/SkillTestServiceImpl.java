package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionConverterService;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.result.service.ResultServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillTestServiceImpl implements SkillTestService {

    @Value("${param.regular.test.size}")
    private Integer regularTestSize;
    @Value("${param.short.test.size}")
    private Integer shortTestSize;

    private final QuestionServiceImpl questionServiceImpl;
    private final SkillTestRepository skillTestRepository;
    private final ContestantServiceImpl contestantServiceImpl;
    private final ResultServiceImpl resultServiceImpl;
    private final SkillTestMapper testMapper;
    private final QuestionConverterService questionConverterService;


    public SkillTestServiceImpl(
            QuestionServiceImpl questionServiceImpl,
            SkillTestRepository skillTestRepository,
            ContestantServiceImpl contestantServiceImpl,
            ResultServiceImpl resultServiceImpl,
            SkillTestMapper testMapper,
            QuestionConverterService questionConverterService) {
        this.questionServiceImpl = questionServiceImpl;
        this.contestantServiceImpl = contestantServiceImpl;
        this.skillTestRepository = skillTestRepository;
        this.resultServiceImpl = resultServiceImpl;
        this.testMapper = testMapper;
        this.questionConverterService = questionConverterService;
    }

    public SkillTestDto findTestByUUID(UUID testUUID) {
        return skillTestRepository.findById(testUUID)
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new NotFoundException("SkillTestService: no test with given Id"));
    }

    public List<SkillTestDto> findTestByContestantNumber(java.lang.String contestantNumber) {

        return skillTestRepository.findByContestant_ContestantNumber(contestantNumber)
                .stream()
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());

    }

    public SkillTestDto createNewTest(java.lang.String contestantNumber, List<TestParameter> testParams) {
        if (contestantNumber == null || !testParametersValidator(testParams)) {
            throw new BadRequestException("SkillTestService: ContestantNumber, SkillLevel or MainTech can't be null");
        }
        SkillTestDto skillTest;
        Optional<ContestantDto> searchResult = contestantServiceImpl.findContestantByNumber(contestantNumber);
        if (searchResult.isEmpty()) {
            ContestantDto contestantDto = ContestantDto.builder()
                    .contestantNumber(contestantNumber)
                    .results(new ArrayList<>())
                    .skillTests(new ArrayList<>())
                    .build();
            contestantServiceImpl.saveOrUpdate(contestantDto);
            skillTest = generateTest(contestantDto, testParams);
        } else {
            skillTest = generateTest(searchResult.get(), testParams);
        }
        skillTestRepository.save(testMapper.dtoToObject(skillTest, new CycleAvoidingMappingContext()));

        return skillTest;
    }

    private SkillTestDto generateTest(ContestantDto contestant, List<TestParameter> testParams) {
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();

        for (TestParameter param : testParams) {
            String mainTech = param.getMainTechParam();
            java.lang.String specificTech = param.getSpecificTechParam();
            Integer qty = param.getQty();
            SkillLevel skillLevel = questionConverterService.convertSkillLevel(param.getSkillLevelParam());

            List<QuestionDto> searchResult = questionServiceImpl.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, specificTech, skillLevel);

            Collections.shuffle(searchResult);

            searchResult.stream()
                    .limit(qty)
                    .forEachOrdered(questionDtos::add);

        }

        Collections.shuffle(questionDtos);

        return SkillTestDto.builder()
                .contestant(contestant)
                .testStatus(java.lang.String.valueOf(TestStatus.BASE))
                .questions(questionDtos)
                .build();

    }


//    private SkillTestDto generateTest(ContestantDto contestant, MainTech mainTech, SkillLevel skillLevel, Boolean isRegularTest, Boolean addCodeBlocks) {
//        SkillTestDto result = SkillTestDto.builder()
//                .contestant(contestant)
//                .testStatus(String.valueOf(TestStatus.BASE))
//                .build();
//
//        int[] codeQuestionPattern = {3, 5, 7, 9, 10};
//
//        HashSet<QuestionDto> questions = new HashSet<>(questionServiceImpl.findAllByMainTechAndSkillLevel(mainTech, skillLevel));
//
//        questions.stream()
//                .filter(question -> !question.getSpecificTech().equals("Code"))
//                .limit(regularTestSize - 5)
//                .forEachOrdered(result.getQuestions()::add);
//
//        List<QuestionDto> codeQuestions = questionServiceImpl.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, "Code", skillLevel);
//        for (int i = 0; i < 5; i++) {
//            result.getQuestions().add(codeQuestionPattern[i], codeQuestions.get(i));
//        }
//        return result;
//    }


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

    private Boolean testParametersValidator(List<TestParameter> testParams) {
        Boolean result = true;
        if (testParams == null || testParams.size() == 0) {
            result = false;
        } else for (TestParameter testParam : testParams) {
            if ((testParam.getMainTechParam() == null) || (testParam.getSpecificTechParam() == null) || (testParam.getSkillLevelParam() == null) || (testParam.getQty() == null)) {
                result = false;
                break;
            }
        }
        return result;
    }


}
