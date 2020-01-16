package com.github.pawelbialas.testgeneratorapp.entity.result.service;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.result.repository.ResultRepository;
import com.github.pawelbialas.testgeneratorapp.entity.test.service.SkillTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class ResultService {


    @Autowired private ResultRepository resultRepository;
    @Autowired private SkillTestService skillTestService;


    

}
