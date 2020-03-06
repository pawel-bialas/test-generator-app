package com.github.pawelbialas.testgeneratorapp.entity.answer.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;


public interface AnswerRepository extends JpaRepository<Answer, UUID> {


   Optional<Answer> findByAnswer(String answer);

   Optional<Answer> findByQuestionId (UUID uuid);

}
