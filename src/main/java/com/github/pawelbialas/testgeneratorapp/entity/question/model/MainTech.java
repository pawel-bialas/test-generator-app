package com.github.pawelbialas.testgeneratorapp.entity.question.model;

public enum MainTech {

    JAVA("JAVA"),
    _NET("_NET"),
    JAVASCRIPT("JAVASCRIPT"),
    PHP("PHP"),
    ANGULAR("ANGULAR"),
    REACT("REACT"),
    FRONT_END("FRONT_END"),
    SQL("SQL"),
    UNASSIGNED("UNASSIGNED"),
    DEVOPS("DEVOPS"),
    DOCKER("DOCKER"),
    SPLUNK("SPLUNK"),
    GO("SQL"),
    BLOCKCHAIN("BLOCKCHAIN")

    ;


    private String technology;

    private MainTech(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
