package com.github.pawelbialas.testgeneratorapp.entity.contestant.service;

import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantDto;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.dto.ContestantMapper;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.repository.ContestantRepository;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository contestantRepository;
    private final EntityManagerFactory emf;
    private final ContestantMapper mapper;

    public ContestantServiceImpl(ContestantRepository contestantRepository, EntityManagerFactory emf, ContestantMapper mapper) {
        this.contestantRepository = contestantRepository;
        this.emf = emf;
        this.mapper = mapper;
    }

    @Override
    public ContestantDto findContestantByNumber(String contestantNumber) {
        try {
            return contestantRepository.findByContestantNumber(contestantNumber)
                    .map(mapper::objectToDto)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException notFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    notFound.getMessage()
            );
        }
    }

    @Override
    public Boolean confirmContestant(String contestantNumber) {
        return contestantRepository.findByContestantNumber(contestantNumber).isPresent();
    }

    @Override
    public Contestant saveOrUpdate( ContestantDto contestantDto) {
            return contestantRepository.findById(mapper.dtoToObject(contestantDto).getId())
                    .map(val -> emf.createEntityManager().merge(mapper.dtoToObject(contestantDto)))
                    .orElse(contestantRepository.save(mapper.dtoToObject(contestantDto)));
    }



    
}
