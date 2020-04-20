package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;

import java.util.List;
import java.util.UUID;

public interface ResultService {


    List<ResultDto> findAllByContestantIdOrContestantNumber(UUID contestantId, String contestantNumber);
}
