package com.github.pawelbialas.testgeneratorapp.entity.result.model;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Candidate;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Result extends BaseEntity {

    @Column(nullable = false)
    private Integer score;
    @OneToOne (fetch = FetchType.LAZY)
    private SkillTest skillTest;
    @ManyToOne (fetch = FetchType.LAZY)
    private Candidate candidate;


    public Result (UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                   Integer score, SkillTest skillTest, Candidate candidate) {
        super(id, version, createdDate, lastModifiedDate);
        this.score = score;
        this.skillTest = skillTest;
        this.candidate = candidate;
    }

}
