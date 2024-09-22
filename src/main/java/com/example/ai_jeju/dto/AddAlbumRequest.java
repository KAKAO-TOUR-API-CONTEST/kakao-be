package com.example.ai_jeju.dto;

import lombok.Getter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
public class AddAlbumRequest {

    private Long childId;
    private JsonNullable<String> albumTitle;
    private JsonNullable<String> albumDesc;
    private List<AlbumItemDto> albumItemDtos;
    private AlbumOptionDto albumOptionDto;
    private String rgtDate;

    public AddAlbumRequest(){
        this.albumTitle = JsonNullable.undefined();
        this.albumDesc = JsonNullable.undefined();
    }
}
