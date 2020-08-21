package com.github.pawelbialas.testgeneratorapp.entity.skilltest.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionConverterService;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestMapper;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestStatus;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository.SkillTestRepository;
import com.github.pawelbialas.testgeneratorapp.shared.exception.BadRequestException;
import com.github.pawelbialas.testgeneratorapp.shared.exception.InternalServerErrorException;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class SkillTestServiceImpl implements SkillTestService {

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
    public SkillTestDto saveOrUpdate(SkillTestDto skillTestDto) {
        SkillTest save = skillTestRepository.save(testMapper.dtoToObject(skillTestDto, contextProvider()));
        return testMapper.objectToDto(save, contextProvider());
    }

    @Override
    public List<SkillTestDto> findAll() {
        return fetchAll().stream()
                .map(val -> testMapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }

    public Optional<SkillTestDto> findTestByUUID(UUID testUUID) {
        return skillTestRepository.findById(testUUID)
                .map(skillTest -> testMapper.objectToDto(skillTest, contextProvider()));

    }


    List<SkillTest> fetchAll() {
        EntityManager em = emf.createEntityManager();
        List<SkillTest> skillTests = em.createQuery(
                "select distinct s from SkillTest s " +
                        "left join fetch s.questions q " +
                        "left join fetch s.contestant c " +
                        "left join fetch s.result r ",
                SkillTest.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        List<Question> questions = skillTests.stream().map(SkillTest::getQuestions).flatMap(List::stream).collect(Collectors.toList());

        List<Question> questionsWithAnswers = em.createQuery(
                "select distinct q from Question q " +
                        "left join fetch q.answers " +
                        "where q in :questions",
                Question.class)
                .setParameter("questions", questions)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        List<SkillTest> join = skillTests.stream().peek(skillTest -> {
            List<Question> questionList = skillTest.getQuestions();
            questionList.forEach(question -> question.setAnswers(questionsWithAnswers.get(questionsWithAnswers.indexOf(question)).getAnswers()));
            skillTest.setQuestions(questionList);
        }).collect(Collectors.toList());
        return join;
    }

    @Override
    public List<SkillTestDto> findAllByContestantNumber(String contestantNumber) {

        return skillTestRepository.findByContestant_ContestantNumber(contestantNumber)
                .stream()
                .map(skillTest -> testMapper.objectToDto(skillTest, contextProvider()))
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
        SkillTest skillTest = skillTestRepository.save(testMapper.dtoToObject(generateTest(contestantDto, testParams), contextProvider()));
        return testMapper.objectToDto(skillTest, contextProvider());
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
