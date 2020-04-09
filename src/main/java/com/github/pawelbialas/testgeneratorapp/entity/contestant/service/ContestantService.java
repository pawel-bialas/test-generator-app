package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;

import java.util.Optional;

public interface ContestantService {

    Optional<ContestantDto> findContestantByNumber (String contestantNumber);

    Boolean confirmContestant(String contestantNumber);

    Contestant saveOrUpdate(ContestantDto contestantDto);
}
