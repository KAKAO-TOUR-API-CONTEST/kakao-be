package com.example.ai_jeju.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@AllArgsConstructor
public class ModifyAlbumRequest {

    private Long albumId;
    private JsonNullable<String> albumTitle;
    private JsonNullable<String> albumDesc;
    // 선택적 필드, null일 수 있음
    // 닉네임 수정해야해 ? 기본키인데?
    // private JsonNullable<String> email;
    // 기본 생성자와 setter 추가


    //이 기본생성자 꼭 ㅠㅠㅠㅠㅠ 생성해야해
    public ModifyAlbumRequest() {
        this.albumTitle = JsonNullable.undefined();
        this.albumDesc = JsonNullable.undefined();
    }

}
