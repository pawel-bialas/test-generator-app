package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;
    private final EntityManager entityManager;


    public AnswerServiceImpl(AnswerRepository repository,
                             AnswerMapper mapper,
                             EntityManager entityManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public AnswerDto saveOrUpdate(@NotNull AnswerDto answerDto) {
        Answer save = repository.save(mapper.dtoToObject(answerDto, contextProvider()));
        return mapper.objectToDto(save, contextProvider());
    }


    @Override
    public List<AnswerDto> findAll() {
        return repository.findAll().stream()
                .map(val -> mapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AnswerDto> findById(UUID uuid) {
        return repository.findById(uuid).map(val -> mapper.objectToDto(val, contextProvider()));
    }

    @Override
    public Optional<AnswerDto> findByAnswer(String answer) {
        return repository.findByAnswer(answer)
                .map(val -> mapper.objectToDto(val, contextProvider()));
    }

    @Override
    public List<AnswerDto> findAllByQuestionId(UUID uuid) {
        return repository.findAllByQuestionId(uuid).stream()
                .map(val -> mapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }


}
