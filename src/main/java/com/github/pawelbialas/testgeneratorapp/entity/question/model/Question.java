package com.github.pawelbialas.testgeneratorapp.entity.question.model;

import com.github.pawelbialas.testgeneratorapp.utils.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Question extends BaseEntity {

    private String contents;

    private String specificTechnology;



}
