package com.github.pawelbialas.testgeneratorapp.entity.candidate.repository;


import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface ContestantRepository extends JpaRepository<Contestant, UUID> {

    Contestant findByContestantNumber (String contestantNumber);

}
