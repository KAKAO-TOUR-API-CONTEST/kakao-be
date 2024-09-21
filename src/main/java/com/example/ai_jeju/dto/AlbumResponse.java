package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.AlbumOption;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AlbumResponse {

    private Long albumId;
    private String rgtDate;

}
