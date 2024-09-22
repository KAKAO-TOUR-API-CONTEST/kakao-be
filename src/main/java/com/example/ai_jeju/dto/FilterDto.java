package com.example.ai_jeju.dto;

import io.swagger.v3.core.util.Json;
import lombok.Builder;
import lombok.Getter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Builder
public class FilterDto {

    private JsonNullable<Boolean> parking;
    private JsonNullable<Boolean> strollerVal;
    private JsonNullable<Boolean> KidsZone;
    private JsonNullable<Boolean> playground;
    private JsonNullable<Boolean> babySpareChair;
    private JsonNullable<Boolean> rcmd;
    private JsonNullable<Integer> categoryId;
    private JsonNullable<String> keyword;

}
