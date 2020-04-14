package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionMapper;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final EntityManagerFactory emf;
    private final QuestionMapper mapper;

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
    public List<QuestionDto> findAllByMainTech(String mainTech) {
        return questionRepository.findAllByMainTech(mainTech).stream()
                .filter(question -> question.getMainTech().equals(mainTech))
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech) {
        return questionRepository.findAllByMainTechAndSpecificTech(mainTech, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevel(mainTech, skillLevel)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }


    public List<QuestionDto> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, java.lang.String specificTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, skillLevel, specificTech)
                .stream()
                .map(question -> mapper.objectToDto(question, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }


}
