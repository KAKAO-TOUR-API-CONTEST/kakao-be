package com.example.ai_jeju.dto;

import lombok.Builder;
import lombok.Getter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Builder
public class FilterDto {
    private JsonNullable<Boolean> parking;
    private JsonNullable<Boolean> strollerVal;
    private JsonNullable<String> noKidsZone;
    private JsonNullable<Boolean> playground;
    private JsonNullable<Boolean> babySpareChair;
    private JsonNullable<Boolean> rcmd;


    public Boolean getParkingOrNull() {
        return parking.orElse(null);
    }

    public Boolean getStrollerVarOrNull() {
        return strollerVal.orElse(null);
    }

    public String getNoKidsZoneOrNull() {
        return noKidsZone.orElse(null);
    }

    public Boolean getPlaygroundOrNull() {
        return playground.orElse(null);
    }

    public Boolean getBabySpareChairOrNull() {
        return babySpareChair.orElse(null);
    }

    public Boolean getRcmdOrNull() {
        return rcmd.orElse(null);
    }

}
