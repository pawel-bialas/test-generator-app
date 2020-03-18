package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.exception.ContestantServiceNotFound;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
@Transactional
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository contestantRepository;
    private final EntityManagerFactory emf;
    private final ContestantMapper mapper;

    public ContestantServiceImpl(ContestantRepository contestantRepository, EntityManagerFactory emf, ContestantMapper mapper) {
        this.contestantRepository = contestantRepository;
        this.emf = emf;
        this.mapper = mapper;
    }

    @Override
    public ContestantDto findContestantByNumber(String contestantNumber) {
            return contestantRepository.findByContestantNumber(contestantNumber)
                    .map(contestant -> mapper.objectToDto(contestant, new CycleAvoidingMappingContext()))
                    .orElseThrow(() -> new ContestantServiceNotFound("There is no contestant with a given Id"));

    }

    @Override
    public Boolean confirmContestant(String contestantNumber) {
        return contestantRepository.findByContestantNumber(contestantNumber).isPresent();
    }

    @Override
    public Contestant saveOrUpdate( ContestantDto contestantDto) {
            return contestantRepository.findById(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext()).getId())
                    .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext())))
                    .orElse(contestantRepository.save(mapper.dtoToObject(contestantDto, new CycleAvoidingMappingContext())));
    }



    
}
