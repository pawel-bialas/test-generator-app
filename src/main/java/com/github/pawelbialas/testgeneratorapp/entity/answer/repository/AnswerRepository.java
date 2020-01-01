package com.github.pawelbialas.testgeneratorapp.entity.answer.repository;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, UUID> {

}
