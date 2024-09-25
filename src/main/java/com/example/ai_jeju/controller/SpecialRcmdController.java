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
    ResponseDto getSpecialRcmd(@RequestParam int rcmdType, int category){
        return ResponseUtil.SUCCESS("특별 추천 조회에 성공하였습니다", specialRcmdService.getSpecialRcmd(rcmdType,category));
    }






}
