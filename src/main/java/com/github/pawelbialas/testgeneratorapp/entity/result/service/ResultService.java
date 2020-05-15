package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResultService {


    ResultDto saveOrUpdate (ResultDto resultDto);

    Optional<ResultDto> findById (UUID uuid);

    List<ResultDto> findAll();

    List<ResultDto> findAllByContestantIdOrContestantNumber(UUID contestantId, String contestantNumber);
}
