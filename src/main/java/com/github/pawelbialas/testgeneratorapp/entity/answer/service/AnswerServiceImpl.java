package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;
    private final EntityManagerFactory emf;


    public AnswerServiceImpl(AnswerRepository repository,
                             AnswerMapper mapper,
                             EntityManagerFactory emf) {
        this.repository = repository;
        this.mapper = mapper;
        this.emf = emf;
    }

    @Override
    public Answer saveOrUpdate(@NotNull AnswerDto answerDto) {
        return repository.findById(answerDto.getId())
                .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(answerDto, new CycleAvoidingMappingContext())))
                .orElse(repository.save(mapper.dtoToObject(answerDto, new CycleAvoidingMappingContext())));
    }


    @Override
    public List<AnswerDto> findAll() {
        return repository.findAll().stream()
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AnswerDto> findByAnswer(String answer) {
        return repository.findByAnswer(answer)
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<AnswerDto> findAllByQuestionId(UUID uuid) {
        return repository.findAllByQuestionId(uuid).stream()
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }


}
