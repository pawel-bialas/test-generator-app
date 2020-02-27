package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;

public interface ContestantService {

    ContestantDto findCandidateByNumber (String contestantNumber);

    Boolean confirmCandidate (String contestantNumber);

    Contestant saveOrUpdate(ContestantDto contestantDto);
}
