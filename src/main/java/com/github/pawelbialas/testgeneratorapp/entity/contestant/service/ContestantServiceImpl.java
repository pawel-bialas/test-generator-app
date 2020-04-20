package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.UUID;

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
    public Contestant saveOrUpdate(ContestantDto contestantDto) {
        return repository.findByContestantNumber(contestantDto.getContestantNumber())
                .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext())))
                .orElse(repository.save(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext())));
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
