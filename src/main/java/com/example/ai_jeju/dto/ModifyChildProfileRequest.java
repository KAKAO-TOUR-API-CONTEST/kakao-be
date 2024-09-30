package com.example.ai_jeju.dto;

public class ModifyChildProfileRequest {

    private Long childId;
    private String birthDate;
    private String realtion;
    private boolean gender;

    // Getter 메서드 수동 추가
    public Long getChildId() {
        return childId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getRealtion() {
        return realtion;
    }

    public Boolean getGender() {
        return gender;
    }
}
