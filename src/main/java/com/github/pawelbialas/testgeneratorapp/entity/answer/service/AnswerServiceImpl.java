package com.github.pawelbialas.testgeneratorapp.entity.answer.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerMapper;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final
    AnswerRepository repository;
    private final
    AnswerMapper answerMapper;


    public AnswerServiceImpl(AnswerRepository repository, AnswerMapper answerMapper) {
        this.repository = repository;
        this.answerMapper = answerMapper;
    }



}
