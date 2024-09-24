package com.example.ai_jeju.controller;

import com.example.ai_jeju.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleItemController {

    private final TokenProvider tokenProvider;

    @Autowired

    public ScheduleItemController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

//    @PostMapping("")
//    public void addScheduleItem(@RequestHeader ("Authorization") String token){
//        // Bearer 토큰 형식에서 "Bearer " 부분 제거
//        String accessToken = token.replace("Bearer ", "");
//        if (tokenProvider.validToken(accessToken)) {
//            Long userId = tokenProvider.getUserId(accessToken);
//            try{
//                scheduleItemService.
//            }catch (Exception e){
//                return ResponseUtil.ERROR(e.getMessage(), null);
//            }
//        } else {
//            return ResponseUtil.ERROR("토큰 유효성 문제가 발생하였습니다.", null);
//        }
//    }

    }


