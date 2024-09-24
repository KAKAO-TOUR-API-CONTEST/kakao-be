package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScheduleResponse {
    List<String> schedules;
}
