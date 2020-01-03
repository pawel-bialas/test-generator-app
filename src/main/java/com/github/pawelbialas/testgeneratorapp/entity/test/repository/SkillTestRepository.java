package com.github.pawelbialas.testgeneratorapp.entity.test.repository;


import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SkillTestRepository extends JpaRepository<SkillTest, UUID> {
}
