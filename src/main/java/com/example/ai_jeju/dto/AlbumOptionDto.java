package com.example.ai_jeju.dto;

import io.swagger.v3.core.util.Json;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
 //기본 생성자 꼭 사용해야한다.
public class AlbumOptionDto {

    private JsonNullable<Boolean> optionalPet;
    private JsonNullable<Boolean> optionalFriend;
    private JsonNullable<Boolean> optionalFamily;
    private JsonNullable<Boolean> optionalMorning;
    private JsonNullable<Boolean> optionalAm;
    private JsonNullable<Boolean> optionalPm;
    private JsonNullable<Boolean> optionalDining;
    private JsonNullable<Boolean> optionalSnack;
    private JsonNullable<Boolean> optionalPlay;
    private JsonNullable<Boolean> optionalStudy;
    private JsonNullable<Boolean> optionalExperience;
    private JsonNullable<Boolean> optionalWalk;

    public AlbumOptionDto(){

        this.optionalPet = JsonNullable.undefined();
        this.optionalFriend = JsonNullable.undefined();
        this.optionalFamily = JsonNullable.undefined();
        this.optionalMorning = JsonNullable.undefined();
        this.optionalAm = JsonNullable.undefined();
        this.optionalPm = JsonNullable.undefined();
        this.optionalDining = JsonNullable.undefined();
        this.optionalSnack = JsonNullable.undefined();
        this.optionalPlay = JsonNullable.undefined();
        this.optionalStudy = JsonNullable.undefined();
        this.optionalExperience = JsonNullable.undefined();
        this.optionalWalk = JsonNullable.undefined();


    }


}
