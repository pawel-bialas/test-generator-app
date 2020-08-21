package com.github.pawelbialas.testgeneratorapp.entity.answer.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Transactional
   @Override
   List<Answer> findAll();

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Transactional
   @Override
   <S extends Answer> S save(S s);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Transactional
   @Override
   Optional<Answer> findById(UUID uuid);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Transactional
   Optional<Answer> findByAnswer(String answer);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Transactional
   List<Answer> findAllByQuestionId (UUID uuid);

}
