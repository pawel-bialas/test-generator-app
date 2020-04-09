package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionConverterService;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto.SkillTestDto;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.TestParameter;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${csv.location.prod}")
    private String csvProdPath;
    @Value("${csv.location.test}")
    private String csvTestPath;

    final QuestionConverterService questionConverterService;
    final SkillTestServiceImpl skillTestService;

    public BootstrapSomeData(QuestionConverterService questionConverterService, SkillTestServiceImpl skillTestService) {

        this.questionConverterService = questionConverterService;
        this.skillTestService = skillTestService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {

        questionConverterService.readQuestionsFromCsv(csvProdPath);


        ArrayList<TestParameter> params = new ArrayList<>();

        TestParameter parameter = TestParameter.builder()
                .mainTechParam("Java")
                .qty(4)
                .skillLevelParam("Junior")
                .specificTechParam("Core")
                .build();

        params.add(parameter);

        SkillTestDto newTest = skillTestService.createNewTest("123", params);

        System.out.println(newTest.getQuestions());


    }
}




