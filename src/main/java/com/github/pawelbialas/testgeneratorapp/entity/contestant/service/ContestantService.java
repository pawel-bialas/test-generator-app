package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
@Transactional
public class ContestantService {

    private final ContestantRepository contestantRepository;
    private final EntityManagerFactory emf;

    @Autowired
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
