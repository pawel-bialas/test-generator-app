package com.github.pawelbialas.testgeneratorapp.entity.answer.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Override
   List<Answer> findAll();

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Override
   <S extends Answer> S save(S s);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   @Override
   Optional<Answer> findById(UUID uuid);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   Optional<Answer> findByAnswer(String answer);

   @EntityGraph(value = "answer.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
   List<Answer> findAllByQuestionId (UUID uuid);

}
