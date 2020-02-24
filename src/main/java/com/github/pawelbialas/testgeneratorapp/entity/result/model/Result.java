package com.github.pawelbialas.testgeneratorapp.entity.result.model;

import com.github.pawelbialas.testgeneratorapp.entity.candidate.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.test.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@Table(name = "results")
@Entity
public class Result extends BaseEntity {

    @Column(nullable = false)
    private Integer score;
    @OneToOne (fetch = FetchType.LAZY)
    private SkillTest skillTest;
    @ManyToOne (fetch = FetchType.LAZY)
    private Contestant contestant;

    public Result () {

    }

    @Builder
    public Result (UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                   Integer score, SkillTest skillTest, Contestant contestant) {
        super(id, version, createdDate, lastModifiedDate);
        this.score = score;
        this.skillTest = skillTest;
        this.contestant = contestant;
    }

    @Override
    public String toString() {
        return "Result{" +
                "score=" + score +
                ", skillTest=" + skillTest +
                ", contestant=" + contestant +
                '}';
    }
}
