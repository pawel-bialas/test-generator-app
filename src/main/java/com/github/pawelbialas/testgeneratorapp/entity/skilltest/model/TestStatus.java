package com.github.pawelbialas.testgeneratorapp.entity.skilltest.model;

public enum TestStatus {

    BASE("BASE"),
    FINISHED("FINISHED"),
    PENDING("PENDING")
    ;

    private String testStatus;

    private TestStatus (String testStatus) {
        this.testStatus = testStatus;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }
}
