package com.github.pawelbialas.testgeneratorapp.entity.result.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.pawelbialas.testgeneratorapp.entity.contestant.model.Contestant;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.domain.model.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@NamedEntityGraph(
//        name = "result.fullJoins",
//
//
//)
public class Result extends BaseEntity {

    @Column(nullable = false)
    private Float score;
    @OneToOne (fetch = FetchType.LAZY)
    private SkillTest skillTest;
    @ManyToOne (fetch = FetchType.LAZY)
    private Contestant contestant;

    @Builder
    public Result (UUID id,
                   Long version,
                   Timestamp createdDate,
                   Timestamp lastModifiedDate,
                   Float score,
                   SkillTest skillTest,
                   Contestant contestant) {
        super(id, version, createdDate, lastModifiedDate);
        this.score = score;
        this.skillTest = skillTest;
        this.contestant = contestant;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + this.getId() + '\'' +
                "score=" + score +
                ", skillTest=" + skillTest.getId() +
                ", contestant=" + contestant.getId() +
                '}';
    }
}
