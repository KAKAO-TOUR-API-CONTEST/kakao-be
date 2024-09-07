package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.AlbumItem;
import lombok.Getter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
public class AddAlbumRequest {

    private Long childId;
    private JsonNullable<String> albumTitle;
    private JsonNullable<String> albumDesc;
    private List<AlbumItemDto> albumItemDtos;

    public AddAlbumRequest(){
        this.albumTitle = JsonNullable.undefined();
        this.albumDesc = JsonNullable.undefined();
    }
}
