package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.MainTech;
import com.github.pawelbialas.testgeneratorapp.shared.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Question findByContentsEquals (String contents);

    List<Question> findAllByMainTech (MainTech mainTech);

    List<Question> findAllByMainTechAndSkillLevel (MainTech mainTech, SkillLevel skillLevel);

    List<Question> findAllBySpecificTechAndSkillLevel (String specificTech, SkillLevel skillLevel);


}
