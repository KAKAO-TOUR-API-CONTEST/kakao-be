package com.example.ai_jeju.service;

import com.example.ai_jeju.config.UUIDOneTimeTokenManager;


public class UUIDTokenService {


    private UUIDOneTimeTokenManager UUIDtokenManager;

    // TokenService 생성자에서 UUIDOneTimeTokenManager를 주입받음
    public UUIDTokenService(UUIDOneTimeTokenManager tokenManager) {
        this.UUIDtokenManager = tokenManager;
    }

    // 토큰 생성 메서드
    public String createToken() {
        return UUIDtokenManager.generateOneTimeToken();
    }

    // 토큰 검증 메서드
    public boolean validateToken(String token) {
        return UUIDtokenManager.validateAndUseToken(token);
    }
}