package com.example.ai_jeju.controller;

import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//HTTP 요청을 처리하는 Controller 클래스
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        String response = userService.signUp(signUpRequest);
        //System.out.println(response);
        return ResponseEntity.ok(response);
    }



    //탈퇴하기
    @GetMapping("/withdraw")
    public ResponseEntity<String> signOut(@RequestBody WithdrawRequest withdrawRequest) {
        String response = userService.withDraw(withdrawRequest);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

}
