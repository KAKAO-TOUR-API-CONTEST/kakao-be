package com.example.ai_jeju.controller;


import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.MainVIewService;
import com.example.ai_jeju.service.SpecialRcmdService;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SpecialRcmdController {
    private final TokenProvider tokenProvider;
    @Autowired
    private SpecialRcmdService specialRcmdService;

    public SpecialRcmdController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/special")
    ResponseDto getSpecialRcmd(@RequestHeader(value = "Authorization", required = false) String token,@RequestParam int rcmdType, int category) {
        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            Long userId = tokenProvider.getUserId(accessToken);
            if (tokenProvider.validToken(accessToken)) {

                return ResponseUtil.SUCCESS("회원 특별 추천 조회에 성공하였습니다", specialRcmdService.getSpecialRcmd(rcmdType, category, userId));
            } else {
                return ResponseUtil.SUCCESS("유효하지 않은 사용자입니다.", null);
            }
        }
            else{
            return ResponseUtil.SUCCESS("비회원 특별 추천 조회에 성공하였습니다", specialRcmdService.getSpecialRcmd(rcmdType, category));
            }
        }

    }


