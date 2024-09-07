package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlbumResponse {

    private Long albumId;
    private String repImgSrc;
    private String title;
}
