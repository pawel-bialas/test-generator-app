package com.github.pawelbialas.testgeneratorapp.entity.result.repository;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface ResultRepository extends JpaRepository<Result, UUID> {



    List<Result> findAllByContestant_IdOrContestant_ContestantNumber(UUID contestant_id, String contestantNumber);
}

