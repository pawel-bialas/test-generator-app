package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository repository;
    private final EntityManagerFactory emf;
    private final ContestantMapper mapper;

    public ContestantServiceImpl(ContestantRepository repository,
                                 EntityManagerFactory emf,
                                 ContestantMapper mapper) {
        this.repository = repository;
        this.emf = emf;
        this.mapper = mapper;
    }

    @Override
    public ContestantDto saveOrUpdate(ContestantDto contestantDto) {
        if (!confirmContestant(contestantDto.getContestantNumber())) {
            contestantDto.setResults(new LinkedHashSet<>());
            contestantDto.setSkillTests(new LinkedHashSet<>());
        }
        Contestant save = repository.save(mapper.dtoToObject(contestantDto, contextProvider()));
        return mapper.objectToDto(save, contextProvider());
    }

    @Override
    public List<ContestantDto> findAll() {
        return repository.findAll().stream()
                .map(val -> mapper.objectToDto(val, contextProvider()))
                .collect(Collectors.toList());
    }

    public Optional<ContestantDto> findById(UUID id) {
        return repository.findById(id)
                .map(contestant -> mapper.objectToDto(contestant, contextProvider()));
    }

    @Override
    public Optional<ContestantDto> findContestantByNumber(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber)
                .map(contestant -> mapper.objectToDto(contestant, contextProvider()));


    }

    @Override
    public Boolean confirmContestant(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber).isPresent();
    }


}
