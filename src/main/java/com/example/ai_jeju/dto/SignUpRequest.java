package com.example.ai_jeju.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String name;
    private String nickname;
    private String email;
    private String profile;

}