package com.example.ai_jeju.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StoreResponse {





    private Long storeId;
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
    Boolean noKidsZone;
    Integer categoryId;
    String operationTime;
    String tel;
    int noBmk;


}
