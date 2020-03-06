package com.github.pawelbialas.testgeneratorapp.entity.contestant.repository;


import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


public interface ContestantRepository extends JpaRepository<Contestant, UUID> {

    Optional<Contestant> findByContestantNumber (String contestantNumber);

}
