package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        questionService.readQuestionsFromCsv();

        List<Question> javaQuestions = questionService.findAllByMainTech(MainTech.JAVA);

        System.out.println(javaQuestions.get(12).getAnswers());


//        for (int i = 0; i < 10; i++) {
//            Answer answer1 = new Answer();
//            Answer answer2 = new Answer();
//            Answer answer3 = new Answer();
//
//            answer1.setAnswer("aaa " + (i + 1));
//            answer2.setAnswer("bbb " + (i + 1));
//            answer3.setAnswer("ccc " + (i + 1));
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
//            question.getAnswers().add(answer1);
//            question.getAnswers().add(answer2);
//            question.getAnswers().add(answer3);
//
//            question.setSpecificTech("Java");
//            question.setSkillLevel(SkillLevel.JUNIOR);
//            question.setMainTech(MainTech.JAVA);
//            question.setContents("Pytanie o numerze " + +(i + 1));
//            questionRepository.save(question);
//
//
//
//
//
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
//
//        List<Question> allByMainTech = questionRepository.findAllByMainTech(MainTech.JAVA);
//
//        allByMainTech.stream().forEach(System.out::println);
//
//        System.out.println(answerRepository.findAll());
//    }
//}




