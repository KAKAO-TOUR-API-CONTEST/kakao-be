package com.example.ai_jeju.controller;

import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    private final TokenProvider tokenProvider;

    public TokenController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("")
    public ResponseDto getMyTokenInfo(@RequestHeader("") String token) {
        // Bearer 토큰 형식에서 "Bearer " 부분 제거
        String accessToken = token.replace("Bearer ", "");
        Date expiredDate = tokenProvider.getExpiredDate(token);
        // 날짜 포맷 지정 (예: yyyy-MM-dd HH:mm:ss)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date를 String으로 변환
        String dateString = formatter.format(expiredDate);
        if (tokenProvider.validToken(token)) {
            return ResponseUtil.SUCCESS(true,dateString);
        } else {
            return ResponseUtil.ERROR(false, null);
        }


    }
}
