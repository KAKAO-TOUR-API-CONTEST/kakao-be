package com.example.ai_jeju.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RecommendRequest {

    private List<Long> restaurants;
    private List<Long> leisures;
    private List<Long> hotels;
}
