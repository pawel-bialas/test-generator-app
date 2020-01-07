package com.github.pawelbialas.testgeneratorapp.entity.answer.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

   Answer findByAnswer(String answer);

   Answer findByQuestionId (UUID uuid);

}
