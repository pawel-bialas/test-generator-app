package com.github.pawelbialas.testgeneratorapp.entity.skilltest.controller;

import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillTestController {

    private final SkillTestServiceImpl skillTestService;

    public SkillTestController(SkillTestServiceImpl skillTestService) {
        this.skillTestService = skillTestService;
    }
}
