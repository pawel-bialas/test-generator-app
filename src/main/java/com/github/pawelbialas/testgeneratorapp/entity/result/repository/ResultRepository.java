package com.github.pawelbialas.testgeneratorapp.entity.result.repository;


import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
}

