package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContestantService {

    List<ContestantDto> findAll ();

    Optional<ContestantDto> findById(UUID uuid);

    Optional<ContestantDto> findContestantByNumber (String contestantNumber);

    Boolean confirmContestant(String contestantNumber);

    Contestant saveOrUpdate(ContestantDto contestantDto);
}
