package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.test.repository.SkillTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final EntityManagerFactory emf;
    private final SkillTestRepository skillTestRepository;

    public BootstrapSomeData(QuestionRepository questionRepository, AnswerRepository answerRepository, EntityManagerFactory emf, SkillTestRepository skillTestRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.emf = emf;
        this.skillTestRepository = skillTestRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        for (int i = 0; i < 10; i++) {
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            Answer answer3 = new Answer();

            answer1.setAnswer("aaa "  + (i + 1));
            answer2.setAnswer("bbb "  + (i + 1));
            answer3.setAnswer("ccc "  + (i + 1));

            answer1.setCorrect(true);
            answer2.setCorrect(false);
            answer3.setCorrect(false);

            Question question = new Question();

            answer1.setQuestion(question);
            answer2.setQuestion(question);
            answer3.setQuestion(question);

            question.add(answer1);
            question.add(answer2);
            question.add(answer3);

            question.setSpecificTech("Java");
            question.setSkillLevel(SkillLevel.JUNIOR);
            question.setMainTech(MainTech.JAVA);
            question.setContents("Pytanie o numerze " +  + (i + 1));
            questionRepository.save(question);
        }

        List<Question> allByMainTech = questionRepository.findAllByMainTech(MainTech.JAVA);

        Candidate candidate = new Candidate();
        candidate.setCandidateNumber(12341234L);

        SkillTest skillTest = new SkillTest();
        skillTest.setQuestions(allByMainTech);
        skillTest.setCandidate(candidate);
        skillTestRepository.save(skillTest);


    }



}
