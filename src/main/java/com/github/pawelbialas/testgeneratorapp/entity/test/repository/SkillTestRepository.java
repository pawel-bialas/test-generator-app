package com.github.pawelbialas.testgeneratorapp.entity.test.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillTestRepository extends JpaRepository<Answer, UUID> {
}
