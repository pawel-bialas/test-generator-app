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
    List<Question> findAll();

    @Override
    <S extends Question> S save(S s);

    @Override
    Optional<Question> findById(UUID uuid);

    Question findByContentsEquals(String contents);

    List<Question> findAllByMainTechAndSpecificTech(String mainTech, java.lang.String specificTech);

    List<Question> findAllByMainTech(String mainTech);

    List<Question> findAllByMainTechAndSkillLevel(String mainTech, SkillLevel skillLevel);

    List<Question> findAllByMainTechAndSkillLevelAndSpecificTech(String mainTech, SkillLevel skillLevel, String specificTech);


}
