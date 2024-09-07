package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.AlbumItem;
import lombok.Getter;

import java.util.List;

@Getter
public class AddAlbumRequest {

    private Long childId;
    private String albumTitle;
    private String albumDesc;
    private List<AlbumItemDto> albumItemDtos;
}
