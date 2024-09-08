package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Getter
@Builder
public class MyJejuResponse {

    private MyJejuMyDto myJejuMyDto;
    private List<MyJejuChildDto> myJejuChildDtos;

}
