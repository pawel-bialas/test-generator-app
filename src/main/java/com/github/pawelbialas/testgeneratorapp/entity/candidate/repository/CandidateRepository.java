package com.github.pawelbialas.testgeneratorapp.entity.candidate.repository;


import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Candidate findCandidateByCandidateNumber (String candidateNumber);

}
