package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository repository;
    private final EntityManagerFactory emf;
    private final ContestantMapper mapper;

    public ContestantServiceImpl(ContestantRepository repository, EntityManagerFactory emf, ContestantMapper mapper) {
        this.repository = repository;
        this.emf = emf;
        this.mapper = mapper;
    }

    @Override
    public ContestantDto saveOrUpdate(ContestantDto contestantDto) {
        Contestant save = repository.save(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext()));
        return mapper.objectToDto(save, new CycleAvoidingMappingContext());
    }

    @Override
    public List<ContestantDto> findAll() {
        return repository.findAll().stream()
                .map(val -> mapper.objectToDto(val, new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContestantDto> findContestantByNumber(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber)
                .map(contestant -> mapper.objectToDto(contestant, new CycleAvoidingMappingContext()));


    }

    public Optional<ContestantDto> findById (UUID id) {
        return repository.findById(id)
                .map(contestant -> mapper.objectToDto(contestant, new CycleAvoidingMappingContext()));
    }

    @Override
    public Boolean confirmContestant(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber).isPresent();
    }






}
