package com.github.pawelbialas.testgeneratorapp.entity.question.model;

public enum SkillLevel {

    ENTRY("ENTRY"),
    JUNIOR("JUNIOR"),
    MID("MID"),
    SENIOR("SENIOR"),
    EXPERT("EXPERT"),
    ALL("ALL"),
    UNASSIGNED("UNASSIGNED")
    ;


    private String skillLevel;

    private SkillLevel (String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
