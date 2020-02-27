package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
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
    public ContestantDto findCandidateByNumber(String contestantNumber) {
        return mapper.objectToDto(contestantRepository.findByContestantNumber(contestantNumber));
    }

    @Override
    public Boolean confirmCandidate(String contestantNumber) {
        return contestantRepository.findByContestantNumber(contestantNumber) != null;
    }

    @Override
    public Contestant saveOrUpdate(ContestantDto contestantDto) {
        if (contestantRepository.findById(mapper.dtoToObject(contestantDto).getId()).isPresent()) {
            return emf.createEntityManager().merge(mapper.dtoToObject(contestantDto));
        } else return contestantRepository.save(mapper.dtoToObject(contestantDto));
    }



    
}
