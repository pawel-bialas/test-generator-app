package com.github.pawelbialas.testgeneratorapp.shared;

public enum MainTech {

    JAVA("JAVA"),
    _NET("_NET"),
    PHP("PHP"),
    ANGULAR("ANGULAR"),
    REACT("REACT"),
    FRONT_END("FRONT_END"),
    SQL("SQL"),
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
