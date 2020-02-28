package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.dto.QuestionDto;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Question findByContentsEquals (String contents);

    List<Question> findAllByMainTechAndSpecificTech (MainTech mainTech, String specificTech);

    List<Question> findAllByMainTech (MainTech mainTech);

    List<Question> findAllByMainTechAndSkillLevel (MainTech mainTech, SkillLevel skillLevel);

    List<Question> findAllByMainTechAndSkillLevelAndSpecificTech (MainTech mainTech, SkillLevel skillLevel, String specificTech);


}
