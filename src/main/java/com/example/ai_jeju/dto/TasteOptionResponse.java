package com.example.ai_jeju.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TasteOptionResponse {

    private Long storeId;
    private String imgSrc;

}
