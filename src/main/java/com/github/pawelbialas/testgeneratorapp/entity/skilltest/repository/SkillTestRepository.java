package com.github.pawelbialas.testgeneratorapp.entity.skilltest.repository;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillTestRepository extends JpaRepository<SkillTest, UUID> {

    @Override
    List<SkillTest> findAll();

    @Override
    <S extends SkillTest> S save(S s);

    @Override
    Optional<SkillTest> findById(UUID uuid);


    List<SkillTest> findByContestant_ContestantNumber (String contestantNumber);
}
