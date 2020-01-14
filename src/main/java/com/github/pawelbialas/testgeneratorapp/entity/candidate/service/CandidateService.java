package com.github.pawelbialas.testgeneratorapp.entity.candidate.service;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CandidateService {

    @Autowired private CandidateRepository candidateRepository;


    public Candidate findCandidateByNumber (String candidateNumber) {
        return candidateRepository.findCandidateByCandidateNumber(candidateNumber);
    }

    public Boolean confirmCandidate () {

    }

}
