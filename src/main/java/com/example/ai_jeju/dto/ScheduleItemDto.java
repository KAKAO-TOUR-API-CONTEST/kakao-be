package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleItemDto {

    private String scheduleTitle;
    private String year;
    private String month;
    private String day;
}
