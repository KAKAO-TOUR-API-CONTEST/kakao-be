package com.example.ai_jeju.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDOneTimeTokenManager {
    private Map<String, LocalDateTime> tokenStore = new HashMap<>();

    // 일회성 토큰 생성
    public String generateOneTimeToken() {
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusDays(1);  // 1일 후 만료
        tokenStore.put(token, expirationTime);
        return token;
    }

    // 토큰 확인 및 사용 처리
    public boolean validateAndUseToken(String token) {
        LocalDateTime expirationTime = tokenStore.get(token);

        if (expirationTime == null || LocalDateTime.now().isAfter(expirationTime)) {
            return false; // 만료되었거나 존재하지 않는 토큰
        }
        tokenStore.remove(token); // 사용된 토큰을 삭제
        return true; // 유효한 토큰
    }

}
