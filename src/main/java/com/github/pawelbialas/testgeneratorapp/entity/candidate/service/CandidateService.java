package com.github.pawelbialas.testgeneratorapp.entity.candidate.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
@Transactional
public class CandidateService {

    @Autowired private CandidateRepository candidateRepository;
    @Autowired private EntityManagerFactory emf;

    public Candidate findCandidateByNumber (String candidateNumber) {
        return candidateRepository.findCandidateByCandidateNumber(candidateNumber);
    }

    public Boolean confirmCandidate (String candidateNumber) {
        return candidateRepository.findCandidateByCandidateNumber(candidateNumber) != null;
    }

    public Candidate saveOrUpdate(Candidate candidate) {
        if (candidateRepository.findById(candidate.getId()).isPresent()) {
            return emf.createEntityManager().merge(candidate);
        }
        return candidateRepository.save(candidate);
    }

    
}
