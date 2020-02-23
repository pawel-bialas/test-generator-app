package com.github.pawelbialas.testgeneratorapp.entity.test.repository;


import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SkillTestRepository extends JpaRepository<SkillTest, UUID> {

    List<SkillTest> findAllByCandidate (Contestant contestant);

    List<SkillTest> findByCandidate_CandidateNumber (String candidateNumber);
}
