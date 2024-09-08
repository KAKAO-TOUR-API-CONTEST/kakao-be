package com.example.ai_jeju.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookMarkItem {

    private Long storeId;
    private String imgsrc;
    private String storeName;
    private boolean noKidszone;
    private int numOfBmk;

}
