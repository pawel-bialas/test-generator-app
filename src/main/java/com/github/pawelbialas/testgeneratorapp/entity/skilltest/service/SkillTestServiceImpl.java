package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionConverterService;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.exception.InternalServerErrorException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
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
    private final SkillTestMapper testMapper;
    private final QuestionConverterService questionConverterService;
    private final ContestantMapper contestantMapper;
    private final EntityManagerFactory emf;


    public SkillTestServiceImpl(
            QuestionServiceImpl questionServiceImpl,
            SkillTestRepository skillTestRepository,
            ContestantServiceImpl contestantServiceImpl,
            SkillTestMapper testMapper,
            QuestionConverterService questionConverterService,
            ContestantMapper contestantMapper,
            EntityManagerFactory emf) {
        this.questionServiceImpl = questionServiceImpl;
        this.contestantServiceImpl = contestantServiceImpl;
        this.skillTestRepository = skillTestRepository;
        this.testMapper = testMapper;
        this.questionConverterService = questionConverterService;
        this.contestantMapper = contestantMapper;
        this.emf = emf;
    }


    @Override
    public SkillTest saveOrUpdate(@NotNull SkillTestDto skillTestDto) {
        return skillTestRepository.findById(skillTestDto.getId())
                .map(val -> emf.createEntityManager().merge(testMapper.dtoToObject(skillTestDto, new CycleAvoidingMappingContext())))
                .orElse(skillTestRepository.save(testMapper.dtoToObject(skillTestDto, new CycleAvoidingMappingContext())));
    }

    public Optional<SkillTestDto> findTestByUUID(UUID testUUID) {
        return skillTestRepository.findById(testUUID)
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()));

    }

    @Override
    public List<SkillTestDto> findAllByContestant(ContestantDto contestant) {
        return skillTestRepository.findAllByContestant(contestantMapper.dtoToObject(contestant, new CycleAvoidingMappingContext()))
                .stream()
                .map(val -> testMapper.objectToDto(val, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SkillTestDto> findAllByContestantNumber(String contestantNumber) {

        return skillTestRepository.findByContestant_ContestantNumber(contestantNumber)
                .stream()
                .map(skillTest -> testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public SkillTestDto createNewTest(String contestantNumber, List<TestParameter> testParams) {
        if (contestantNumber == null || !testParametersValidator(testParams)) {
            throw new BadRequestException("SkillTestService: ContestantNumber, SkillLevel or MainTech can't be null");
        }
        ContestantDto contestantDto = verifyContestantNumber(contestantNumber);
        if (contestantDto == null) {
            throw new InternalServerErrorException("Something went wrong ... Server side problem");
        }
        SkillTest skillTest = skillTestRepository.save(testMapper.dtoToObject(generateTest(contestantDto, testParams), new CycleAvoidingMappingContext()));
        return testMapper.objectToDto(skillTest, new CycleAvoidingMappingContext());
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


    private ContestantDto verifyContestantNumber(String contestantNumber) {
        Boolean confirmation = contestantServiceImpl.confirmContestant(contestantNumber);
        ContestantDto contestantDto = null;
        if (!confirmation) {
             contestantDto = ContestantDto.builder()
                    .contestantNumber(contestantNumber)
                    .results(new ArrayList<>())
                    .skillTests(new ArrayList<>())
                    .build();
            UUID newId = contestantServiceImpl.saveOrUpdate(contestantDto).getId();
            Optional<ContestantDto> byId = contestantServiceImpl.findById(newId);
            if (byId.isPresent()) {
                 contestantDto = byId.get();
            }
        } else {
            Optional<ContestantDto> queryResult = contestantServiceImpl.findContestantByNumber(contestantNumber);
            if (queryResult.isPresent()) {
                contestantDto = queryResult.get();
            }
        }
        return contestantDto;
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
