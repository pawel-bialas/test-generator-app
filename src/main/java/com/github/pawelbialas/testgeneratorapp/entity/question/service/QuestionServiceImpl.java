package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;
    private final EntityManagerFactory emf;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            EntityManagerFactory emf,
            QuestionMapper mapper
    ) {
        this.questionRepository = questionRepository;
        this.mapper = mapper;
        this.emf = emf;
    }

    @Override
    public QuestionDto saveOrUpdate(QuestionDto questionDto) {
        Question save = questionRepository.save(mapper.dtoToObject(questionDto, contextProvider()));
        return mapper.objectToDto(save, contextProvider());
    }


    public QuestionDto findByUuId(UUID uuid) {
        Question question = emf.createEntityManager().createQuery("select q from Question q " +
                "left join fetch q.answers " +
                "where q.id = :uuid", Question.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        return mapper.objectToDto(question, contextProvider());
    }

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions =
                emf.createEntityManager().createQuery(
                        "select q from Question q " +
                                "left join fetch q.answers", Question.class)
                        .getResultList();
        return questions.stream().map(question -> mapper.objectToDto(question, contextProvider())).collect(Collectors.toList());
    }


    @Override
    public List<QuestionDto> findAllByMainTech(String mainTech) {
        return questionRepository.findAllByMainTech(mainTech).stream()
                .filter(question -> question.getMainTech().equals(mainTech))
                .map(question -> mapper.objectToDto(question, contextProvider()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech) {
        return questionRepository.findAllByMainTechAndSpecificTech(mainTech, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, contextProvider()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevel(mainTech, skillLevel)
                .stream()
                .map(question -> mapper.objectToDto(question, contextProvider()))
                .collect(Collectors.toList());
    }


    public List<QuestionDto> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, java.lang.String specificTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, skillLevel, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, contextProvider()))
                .collect(Collectors.toList());
    }


}
