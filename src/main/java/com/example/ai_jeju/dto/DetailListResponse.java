package com.example.ai_jeju.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DetailListResponse {

    private Long storeId;
    private String item;
    String name;
    //이미지 소스
    String imgSrc;
    String address;
    double mapX;
    double mapY;
    Boolean stroller;
    Boolean strollerVal;
    Boolean babySpareChair;
    Boolean playground;
    String noKidsZone;
    Integer categoryId;
    String operationTime;
    String tel;
    int noBmk;
    boolean bmkStatus;

}
