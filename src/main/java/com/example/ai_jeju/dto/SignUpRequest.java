package com.example.ai_jeju.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequest {

    private String name;
    private String nickname;
    private String email;
    private String profile;
    private String provider;

    private List<String> name;

}