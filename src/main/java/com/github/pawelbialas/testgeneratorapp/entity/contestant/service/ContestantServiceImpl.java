package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContextProvider.contextProvider;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository repository;
    private final EntityManager entityManager;
    private final ContestantMapper mapper;

    public ContestantServiceImpl(
            ContestantRepository repository,
            EntityManager entityManager,
            ContestantMapper mapper
    ) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public ContestantDto saveOrUpdate(ContestantDto contestantDto) {
        if (!confirmContestant(contestantDto.getContestantNumber())) {
            contestantDto.setResults(new ArrayList<>());
            contestantDto.setSkillTests(new ArrayList<>());
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

    @Override
    public Optional<ContestantDto> findContestantByNumber(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber)
                .map(contestant -> mapper.objectToDto(contestant, contextProvider()));


    }

    public Optional<ContestantDto> findById(UUID id) {
        return repository.findById(id)
                .map(contestant -> mapper.objectToDto(contestant, contextProvider()));
    }

    @Override
    public Boolean confirmContestant(String contestantNumber) {
        return repository.findByContestantNumber(contestantNumber).isPresent();
    }


}
