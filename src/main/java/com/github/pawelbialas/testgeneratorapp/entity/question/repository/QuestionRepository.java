package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    List<Question> findAll();

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    <S extends Question> S save(S s);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Optional<Question> findById(UUID uuid);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    Question findByContentsEquals(String contents);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    List<Question> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    List<Question> findAllByMainTech(String mainTech);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    List<Question> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel);

//    @EntityGraph(value = "question.fullJoins", type = EntityGraph.EntityGraphType.FETCH)
    List<Question> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, SkillLevel skillLevel, String specificTech);


}
