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
//
//        questionConverterService.readQuestionsFromCsv(csvProdPath);
//
//
//        ArrayList<TestParameter> params = new ArrayList<>();
//
//        TestParameter parameter1 = TestParameter.builder()
//                .mainTechParam("Java")
//                .qty(2)
//                .skillLevelParam("Junior")
//                .specificTechParam("Core")
//                .build();
//
//        TestParameter parameter2 = TestParameter.builder()
//                .mainTechParam("Java")
//                .qty(3)
//                .skillLevelParam("Senior")
//                .specificTechParam("Core")
//                .build();
//
//        params.add(parameter1);
//        params.add(parameter2);
//
//        SkillTestDto newTest = skillTestService.createNewTest("123", params);
//
//        System.out.println("=======================");
//        System.out.println(newTest.getQuestions());
//        System.out.println("=======================");
//        System.out.println("this tes has1: " + newTest.getQuestions().size());
//        System.out.println("=======================");
//
//        SkillTestDto newTest1 = skillTestService.createNewTest("456", params);
//
//        System.out.println("=======================");
//        System.out.println(newTest1.getQuestions());
//        System.out.println("=======================");
//        System.out.println("this tes has2: " + newTest1.getQuestions().size());
//        System.out.println("=======================");
//
//        SkillTestDto newTest2 = skillTestService.createNewTest("789", params);
//
//        System.out.println("=======================");
//        System.out.println(newTest2.getQuestions());
//        System.out.println("=======================");
//        System.out.println("this tes has3: " + newTest2.getQuestions().size());
//        System.out.println("=======================");
//
//        SkillTestDto newTest3 = skillTestService.createNewTest("123", params);
//
//        System.out.println("=======================");
//        System.out.println(newTest3.getQuestions());
//        System.out.println("=======================");
//        System.out.println("this tes has4: " + newTest3.getQuestions().size());
//        System.out.println("=======================");


    }
}




