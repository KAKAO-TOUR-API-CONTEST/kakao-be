package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.AlbumOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlbumListResponse {
    private Long albumId;
    private String title;
    private String repImgSrc;
    private AlbumOption albumOption;

}
