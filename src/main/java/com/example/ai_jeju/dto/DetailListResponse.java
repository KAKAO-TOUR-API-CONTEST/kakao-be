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
    String checkin;
    String checkout;

    Boolean stroller;
    Boolean parking; //주차
    Boolean strollerVal; //유모차 편의성
    Boolean babySpareChair; //유아의자 제공
    Boolean playground; //놀이방
    Boolean pet; //반려동물

    String noKidsZone;
    String nokidsdetail;
    Integer categoryId;
    String operationTime;
    String tel;
    int noBmk;
    boolean bmkStatus;

}
