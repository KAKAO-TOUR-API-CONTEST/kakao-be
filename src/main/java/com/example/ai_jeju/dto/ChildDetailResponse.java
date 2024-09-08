package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildDetailResponse {

    private String name;
    private String birthDate;
    private int age;
    private boolean gender;
    private String profileImg;
    private String relation;
    private int months;

}
