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
    String name;
    //이미지 소스
    String imgSrc;
    String address;
    String noKidsZone;
    int noBmk;
    boolean bmkSatus;
}
