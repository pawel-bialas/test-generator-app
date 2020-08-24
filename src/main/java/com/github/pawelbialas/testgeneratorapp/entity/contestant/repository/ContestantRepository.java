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
    List<Contestant> findAll();

    @Override
    <S extends Contestant> S save(S s);

    @Override
    Optional<Contestant> findById(UUID uuid);

    Optional<Contestant> findByContestantNumber(String contestantNumber);

}
