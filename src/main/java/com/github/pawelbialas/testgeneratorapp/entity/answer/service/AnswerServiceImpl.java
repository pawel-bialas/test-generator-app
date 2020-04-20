package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto;
import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.UUID;

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
    public Answer saveOrUpdate(AnswerDto answerDto) {
        return repository.findById(answerDto.getId())
                .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(answerDto, new CycleAvoidingMappingContext())))
                .orElse(repository.save(mapper.dtoToObject(answerDto, new CycleAvoidingMappingContext())));
    }


    @Override
    public Optional<AnswerDto> findByAnswer(String answer) {
        return repository.findByAnswer(answer)
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<AnswerDto> findByQuestionId(UUID uuid) {
        return repository.findByQuestionId(uuid)
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()));
    }


}
