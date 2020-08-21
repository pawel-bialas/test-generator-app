package com.github.pawelbialas.testgeneratorapp.entity.contestant.repository;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant, UUID> {


    @Override
    @Transactional
    List<Contestant> findAll();

    @Override
    @Transactional
    <S extends Contestant> S save(S s);

    @Override
    @Transactional
    Optional<Contestant> findById(UUID uuid);

    @Transactional
    Optional<Contestant> findByContestantNumber(String contestantNumber);

}
