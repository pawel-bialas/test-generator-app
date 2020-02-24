package com.github.pawelbialas.testgeneratorapp.entity.candidate.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.ContestantRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
@Transactional
public class ContestantService {

    private final ContestantRepository contestantRepository;
    private final EntityManagerFactory emf;

    public ContestantService(ContestantRepository contestantRepository, EntityManagerFactory emf) {
        this.contestantRepository = contestantRepository;
        this.emf = emf;
    }

    public Contestant findCandidateByNumber (String contestantNumber) {
        return contestantRepository.findByContestantNumber(contestantNumber);
    }

    public Boolean confirmCandidate (String contestantNumber) {
        return contestantRepository.findByContestantNumber(contestantNumber) != null;
    }

    public Contestant saveOrUpdate(Contestant contestant) {
        if (contestantRepository.findById(contestant.getId()).isPresent()) {
            return emf.createEntityManager().merge(contestant);
        }
        return contestantRepository.save(contestant);
    }

    
}
