package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestParameter {

    private String mainTechParam;
    private String specificTechParam;
    private Integer qty;
    private Boolean codeAssignments;
}
