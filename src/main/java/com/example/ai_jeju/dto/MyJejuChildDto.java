package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyJejuChildDto {

    private Long childId;
    private String relation;
    private String birthDate;
    private int order;
    private int age;
    private int months;
    private String imgSrc;

}
