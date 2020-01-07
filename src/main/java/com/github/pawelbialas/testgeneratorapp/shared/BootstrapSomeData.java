package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private QuestionService questionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        questionService.readQuestionsFromCsv();

        System.out.println(questionService.findAllByMainTech(MainTech.JAVA));


//        for (int i = 0; i < 10; i++) {
//            Answer answer1 = new Answer();
//            Answer answer2 = new Answer();
//            Answer answer3 = new Answer();
//
//            answer1.setAnswer("aaa "  + (i + 1));
//            answer2.setAnswer("bbb "  + (i + 1));
//            answer3.setAnswer("ccc "  + (i + 1));
//
//            answer1.setCorrect(true);
//            answer2.setCorrect(false);
//            answer3.setCorrect(false);
//
//            Question question = new Question();
//
//            answer1.setQuestion(question);
//            answer2.setQuestion(question);
//            answer3.setQuestion(question);
//
//            question.add(answer1);
//            question.add(answer2);
//            question.add(answer3);
//
//            question.setSpecificTech("Java");
//            question.setSkillLevel(SkillLevel.JUNIOR);
//            question.setMainTech(MainTech.JAVA);
//            question.setContents("Pytanie o numerze " +  + (i + 1));
//            questionRepository.save(question);
//        }

//        List<Question> allByMainTech = questionRepository.findAllByMainTech(MainTech.JAVA);
//
//        Candidate candidate = new Candidate();
//        candidate.setCandidateNumber(12341234L);
//
//        SkillTest skillTest = new SkillTest();
//        skillTest.setQuestions(allByMainTech);
//        skillTest.setCandidate(candidate);
//        skillTestRepository.save(skillTest);


    }
}




