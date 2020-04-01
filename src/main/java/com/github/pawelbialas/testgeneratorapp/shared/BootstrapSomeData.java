package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.service.QuestionServiceImpl;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.service.SkillTestServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    private final QuestionServiceImpl questionServiceImpl;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SkillTestServiceImpl skillTestServiceImpl;
    private final ContestantRepository contestantRepository;

    public BootstrapSomeData(QuestionServiceImpl questionServiceImpl, QuestionRepository questionRepository, AnswerRepository answerRepository, SkillTestServiceImpl skillTestServiceImpl, ContestantRepository contestantRepository) {
        this.questionServiceImpl = questionServiceImpl;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.skillTestServiceImpl = skillTestServiceImpl;
        this.contestantRepository = contestantRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {




    }
}




