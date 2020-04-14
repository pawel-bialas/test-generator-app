package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;


public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Question findByContentsEquals (java.lang.String contents);

    List<Question> findAllByMainTechAndSpecificTech (String mainTech, java.lang.String specificTech);

    List<Question> findAllByMainTech (String mainTech);

    List<Question> findAllByMainTechAndSkillLevel (String mainTech, SkillLevel skillLevel);

    List<Question> findAllByMainTechAndSkillLevelAndSpecificTech (String mainTech, SkillLevel skillLevel, java.lang.String specificTech);


}
