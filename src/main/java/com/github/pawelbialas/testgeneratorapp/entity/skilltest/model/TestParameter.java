package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestParameter {

    private String mainTechParam;
    private String skillLevelParam;
    private String specificTechParam;
    private Integer qty;
}
