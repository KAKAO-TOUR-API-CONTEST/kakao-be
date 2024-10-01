package com.example.ai_jeju.dto;

public class ModifyChildProfileRequest {

    private Long childId;
    private String birthDate;
    private String relation;
    private boolean gender;

    public Long getChildId() {
        return childId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getRelation() {
        return relation;
    }

    public Boolean getGender() {
        return gender;
    }
}
