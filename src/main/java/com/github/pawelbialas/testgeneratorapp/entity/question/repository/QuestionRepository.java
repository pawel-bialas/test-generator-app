package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.MainTech;
import com.github.pawelbialas.testgeneratorapp.shared.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Question findByContentsEquals (String contents);

    Set<Question> findAllByMainTech (MainTech mainTech);

    Set<Question> findAllByMainTechAndSkillLevel (MainTech mainTech, SkillLevel skillLevel);

    Set<Question> findAllBySpecificTechAndSkillLevel (String specificTech, SkillLevel skillLevel);


}
