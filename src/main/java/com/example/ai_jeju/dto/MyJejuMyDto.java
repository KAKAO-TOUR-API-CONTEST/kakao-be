package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyJejuMyDto {
    private Long userId;
    private String name;
    private int numOfChild;
    private String rgtDate;
    private String imgSrc;
}
