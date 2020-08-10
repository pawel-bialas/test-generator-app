package com.github.pawelbialas.testgeneratorapp.entity.contestant.repository;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant, UUID> {

    @EntityGraph(value = "contestant.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    List<Contestant> findAll();

    @EntityGraph(value = "contestant.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    <S extends Contestant> S save(S s);

    @EntityGraph(value = "contestant.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Optional<Contestant> findById(UUID uuid);

    @EntityGraph(value = "contestant.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Contestant> findByContestantNumber (String contestantNumber);

}
