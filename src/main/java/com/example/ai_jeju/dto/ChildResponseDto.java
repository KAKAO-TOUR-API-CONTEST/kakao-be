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
    private String age;
    private List<ScheduleItemDto> scheduleItems;
}
