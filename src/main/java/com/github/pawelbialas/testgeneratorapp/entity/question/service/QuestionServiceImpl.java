package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.entity.answer.exception.AnswerNotAcceptable;
import com.github.pawelbialas.testgeneratorapp.entity.question.exception.QuestionBadRequest;
import com.github.pawelbialas.testgeneratorapp.entity.question.exception.QuestionInternalServerError;
import com.github.pawelbialas.testgeneratorapp.entity.question.exception.QuestionNotFound;
import com.opencsv.CSVReader;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final EntityManagerFactory emf;
    private final QuestionMapper mapper;
    @Value("${csv.location.prod}")
    private String csvProdPath;
    @Value("${csv.location.test}")
    private String csvTestPath;


    public QuestionServiceImpl(QuestionRepository questionRepository, EntityManagerFactory emf, QuestionMapper mapper) {
        this.questionRepository = questionRepository;
        this.emf = emf;
        this.mapper = mapper;
    }

    @Override
    public Question saveOrUpdate(@NotNull QuestionDto questionDto) {
        return questionRepository.findById(mapper.dtoToObject(questionDto, new CycleAvoidingMappingContext()).getId())
                .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(questionDto, new CycleAvoidingMappingContext())))
                .orElse(questionRepository.save(mapper.dtoToObject(questionDto, new CycleAvoidingMappingContext())));
    }

    @Override
    public List<QuestionDto> findAll() {
        return questionRepository.findAll().stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTech(MainTech mainTech) {
        return questionRepository.findAllByMainTech(mainTech).stream()
                .filter(question -> question.getMainTech().equals(mainTech))
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSpecificTech(MainTech mainTech, String specificTech) {
        return questionRepository.findAllByMainTechAndSpecificTech(mainTech, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSkillLevel(MainTech mainTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevel(mainTech, skillLevel)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }


    public List<QuestionDto> findAllByMainTechAndSkillLevelAndSpecificTech(MainTech mainTech, String specificTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, skillLevel, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }


    public void readQuestionsFromCsv(String fileLocation) {
//        fileLocation = csvTestPath;
        try {
            CSVReader reader = new CSVReader(new FileReader(fileLocation), ',');
            try {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Question question = new Question();
                    question.setMainTech(convertMainTech(nextLine[1]));
                    question.setSpecificTech(nextLine[2]);
                    question.setSkillLevel(convertSkillLevel(nextLine[3]));
                    question.setContents(nextLine[4]);
                    ArrayList<String> rawAnswers = new ArrayList<>();
                    rawAnswers.add(nextLine[5]);
                    rawAnswers.add(nextLine[6]);
                    rawAnswers.add(nextLine[7]);
                    rawAnswers.add(nextLine[8]);
                    rawAnswers.add(nextLine[9]);
                    List<Answer> answers = convertAnswers(rawAnswers);
                    for (Answer answer : answers) {
                        answer.setQuestion(question);
                        question.getAnswers().add(answer);
                    }
                    questionRepository.save(question);

                }
            } catch (IOException ioException) {
                throw new QuestionInternalServerError("Error while reading the file. Please try again later.");
            }
        } catch (FileNotFoundException fileNotFound) {
            throw new QuestionNotFound("Error while reading the file. The file was not found.");
        }
    }

    private SkillLevel convertSkillLevel(String skillString) {

            SkillLevel outcome = null;
            if (!skillString.isBlank()) {
                switch (skillString) {
                    case "Entry":
                        outcome = SkillLevel.ENTRY;
                        break;
                    case "Junior":
                        outcome = SkillLevel.JUNIOR;
                        break;
                    case "Mid":
                        outcome = SkillLevel.MID;
                        break;
                    case "Senior":
                        outcome = SkillLevel.SENIOR;
                        break;
                    case "Expert":
                        outcome = SkillLevel.EXPERT;
                        break;
                }
            } else throw new QuestionBadRequest("Missing value for the property of SkillLevel");
            return outcome;
    }


    private MainTech convertMainTech(String techString) {
            MainTech outcome = null;
            if (!techString.isBlank()) {
                switch (techString) {
                    case "Java":
                        outcome = MainTech.JAVA;
                        break;
                    case "PHP":
                        outcome = MainTech.PHP;
                        break;
                    case "Angular":
                        outcome = MainTech.ANGULAR;
                        break;
                    default:
                        outcome = MainTech.UNASSIGNED;
                }
            } else throw new QuestionBadRequest("Missing value for the property of MainTech");
            return outcome;
    }


    private List<Answer> convertAnswers(ArrayList<String> answers) {
        try {
            List<Answer> outcome = new ArrayList<>();
            List<Integer> correct = new ArrayList<>();
            if (answers.get(4).length() != 1) {
                String[] split = answers.get(4).split(",");
                for (int i = 0; i < split.length; i++) {
                    int answerNumber = Integer.parseInt(split[i]);
                    correct.add(answerNumber);
                }
            } else {
                correct.add(Integer.parseInt(answers.get(4)));
            }
            for (int i = 0; i < answers.size() - 1; i++) {
                Answer answer = new Answer();
                answer.setAnswer(answers.get(i));
                if (correct.contains(i + 1)) {
                    answer.setCorrect(true);
                } else answer.setCorrect(false);
                outcome.add(answer);
            }
            return outcome;
        } catch (NumberFormatException badData) {
            throw new AnswerNotAcceptable("Bad format of the document. Check answer lines containing characters instead of numbers");
        }
    }


}
