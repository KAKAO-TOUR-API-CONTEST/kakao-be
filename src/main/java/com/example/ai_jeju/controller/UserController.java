package com.example.ai_jeju.controller;

import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.handler.SignUpHandler;
import com.example.ai_jeju.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    /*여기 확인해보자*/
    @PostMapping("/register")
    public void signUp(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.signUp(signUpRequest,request,response);
    }
    @GetMapping("/checks")
    public Object CheckIfUser(@RequestParam String email){
        return userService.checkIfUser(email);
    }

    @GetMapping("/RandomList")
    public Object GetRandomList(@RequestParam String email){
        return userService.checkIfUser(email);
    }

    //탈퇴하기
    @GetMapping("/withdraw")
    public ResponseEntity<String> signOut(@RequestBody WithdrawRequest withdrawRequest) {
        String response = userService.withDraw(withdrawRequest);

        return ResponseEntity.ok(response);
    }

}
