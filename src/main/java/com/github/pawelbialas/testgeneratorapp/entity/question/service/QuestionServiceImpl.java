package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.opencsv.CSVReader;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        ioException.getMessage()
                );
            }
        } catch (FileNotFoundException fileNotFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    fileNotFound.getMessage()
            );
        }
    }

    private SkillLevel convertSkillLevel(String skillString) {
        try {
            SkillLevel result = null;
            if (!skillString.isBlank()) {
                switch (skillString) {
                    case "Entry":
                        result = SkillLevel.ENTRY;
                        break;
                    case "Junior":
                        result = SkillLevel.JUNIOR;
                        break;
                    case "Mid":
                        result = SkillLevel.MID;
                        break;
                    case "Senior":
                        result = SkillLevel.SENIOR;
                        break;
                    case "Expert":
                        result = SkillLevel.EXPERT;
                        break;
                }
            } else throw new IllegalArgumentException("message name to change");
            return result;
        } catch (IllegalArgumentException illegalArgument) {
            throw new ResponseStatusException(
                    HttpStatus.PARTIAL_CONTENT,
                    illegalArgument.getMessage()
            );
        }

    }


    private MainTech convertMainTech(String techString) {
        try {
            MainTech result = null;
            if (!techString.isBlank()) {
                switch (techString) {
                    case "Java":
                        result = MainTech.JAVA;
                        break;
                    case "PHP":
                        result = MainTech.PHP;
                        break;
                    case "ANGULAR":
                        result = MainTech.ANGULAR;
                        break;
                    default:
                        result = MainTech.UNASSIGNED;
                }
            } else throw new IllegalArgumentException("message name to change");
            return result;
        } catch (IllegalArgumentException illegalArgument) {
            throw new ResponseStatusException(
                    HttpStatus.PARTIAL_CONTENT,
                    illegalArgument.getMessage()
            );
        }
    }


    private List<Answer> convertAnswers(ArrayList<String> answers) {
        try {
            List<Answer> result = new ArrayList<>();
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
                result.add(answer);
            }
            return result;
        } catch (NumberFormatException badData) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    badData.getMessage()
            );
        }
    }


}
