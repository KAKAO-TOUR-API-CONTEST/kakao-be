package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.ScheduleItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChildResponseDto {

    private Long childId;
    private String childName;
    private String birthDate;
    private int age;
    private String profieImg;
   // private List<ScheduleItemDto> scheduleItems;
    private List<AlbumResponse> album;

}
