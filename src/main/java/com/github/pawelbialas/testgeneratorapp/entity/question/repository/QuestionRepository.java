package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Override
    @Transactional
    List<Question> findAll();

    @Override
    @Transactional
    <S extends Question> S save(S s);

    @Override
    @Transactional
    Optional<Question> findById(UUID uuid);

    @Transactional
    Question findByContentsEquals(String contents);

    @Transactional
    List<Question> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech);

    @Transactional
    List<Question> findAllByMainTech(String mainTech);

    @Transactional
    List<Question> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel);

    @Transactional
    List<Question> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, SkillLevel skillLevel, String specificTech);


}
