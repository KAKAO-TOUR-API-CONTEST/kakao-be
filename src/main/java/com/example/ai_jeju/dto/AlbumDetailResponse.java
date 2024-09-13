package com.example.ai_jeju.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AlbumDetailResponse {


    private String childName;
    private String rgtDate;
    private String AlbumTitle;
    private String AlbumDesc;
    private List<ImgSrcDto> imgSrcDtos;

}
