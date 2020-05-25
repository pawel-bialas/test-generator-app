package com.github.pawelbialas.testgeneratorapp.entity.contestant.controller;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.service.ContestantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestantController {

    private final ContestantServiceImpl contestantService;

    public ContestantController(ContestantServiceImpl contestantService) {

        this.contestantService = contestantService;
    }


}
