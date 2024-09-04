package com.example.ai_jeju.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;

@Getter
public class ChildRequest {

    private String birthDate;
    private String childName;
    private Boolean gender;
    private long childProfile;
    private String relation;
}
