package com.github.pawelbialas.testgeneratorapp.entity.question.repository;


import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
