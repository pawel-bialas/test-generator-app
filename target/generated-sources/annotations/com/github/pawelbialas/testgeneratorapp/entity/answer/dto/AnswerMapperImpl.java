package com.github.pawelbialas.testgeneratorapp.entity.answer.dto;

import com.github.pawelbialas.testgeneratorapp.entity.answer.dto.AnswerDto.AnswerDtoBuilder;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer.AnswerBuilder;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-26T18:29:27+0100",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.6 (Oracle Corporation)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public Answer dtoToObject(AnswerDto answerDto) {
        if ( answerDto == null ) {
            return null;
        }

        AnswerBuilder answer = Answer.builder();

        answer.id( answerDto.getId() );
        answer.version( answerDto.getVersion() );
        answer.createdDate( dateMapper.asTimestamp( answerDto.getCreatedDate() ) );
        answer.lastModifiedDate( dateMapper.asTimestamp( answerDto.getLastModifiedDate() ) );
        answer.answer( answerDto.getAnswer() );
        answer.correct( answerDto.getCorrect() );

        return answer.build();
    }

    @Override
    public AnswerDto objectToDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerDtoBuilder answerDto = AnswerDto.builder();

        answerDto.id( answer.getId() );
        answerDto.version( answer.getVersion() );
        answerDto.createdDate( dateMapper.asOffsetDateTime( answer.getCreatedDate() ) );
        answerDto.lastModifiedDate( dateMapper.asOffsetDateTime( answer.getLastModifiedDate() ) );
        answerDto.answer( answer.getAnswer() );
        answerDto.correct( answer.getCorrect() );

        return answerDto.build();
    }
}
