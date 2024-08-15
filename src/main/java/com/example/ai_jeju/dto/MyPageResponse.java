package com.example.ai_jeju.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponse {



    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private String rgtDate;
    private String phoneNum;

    private int numOfChilds;
}
