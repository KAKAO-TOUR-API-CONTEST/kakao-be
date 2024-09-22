package com.example.ai_jeju.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MainListResponse {

    private Long storeId;
    private String name;
    //이미지 소스
    private String imgSrc;
    private String address;
    private String noKidsZone;
    int noBmk;
    boolean bmkSatus;
    private Long categoryId;
}
