package com.github.pawelbialas.testgeneratorapp.entity.candidate.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
@Transactional
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final EntityManagerFactory emf;

    public CandidateService(CandidateRepository candidateRepository, EntityManagerFactory emf) {
        this.candidateRepository = candidateRepository;
        this.emf = emf;
    }

    public Contestant findCandidateByNumber (String candidateNumber) {
        return candidateRepository.findCandidateByCandidateNumber(candidateNumber);
    }

    public Boolean confirmCandidate (String candidateNumber) {
        return candidateRepository.findCandidateByCandidateNumber(candidateNumber) != null;
    }

    public Contestant saveOrUpdate(Contestant contestant) {
        if (candidateRepository.findById(contestant.getId()).isPresent()) {
            return emf.createEntityManager().merge(contestant);
        }
        return candidateRepository.save(contestant);
    }

    
}
