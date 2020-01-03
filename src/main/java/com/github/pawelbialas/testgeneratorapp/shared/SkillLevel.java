package com.github.pawelbialas.testgeneratorapp.shared;

public enum SkillLevel {

    ENTRY("ENTRY"),
    JUNIOR("JUNIOR"),
    MID("MID"),
    SENIOR("SENIOR"),
    EXPERT("EXPERT"),
    ALL("ALL")
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
