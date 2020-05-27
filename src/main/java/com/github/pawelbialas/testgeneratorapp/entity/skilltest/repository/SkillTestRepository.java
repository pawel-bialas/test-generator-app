package com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillTestRepository extends JpaRepository<SkillTest, UUID> {

    List<SkillTest> findAllByContestant (Contestant contestant);

    List<SkillTest> findByContestant_ContestantNumber (String contestantNumber);
}
