package com.example.ai_jeju.controller;

import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    @PostMapping("/register")
    public ResponseDto registerUser(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

        return userService.registerUser(signUpRequest,request,response);
    }

    @GetMapping("/checks")
    public ResponseDto CheckIfUser(@RequestParam(name = "email") String email, HttpServletRequest request, HttpServletResponse response){

        String [] result = userService.checkIfUser(email, request, response);
        if(result == null){
            return ResponseUtil.FAILURE("없는 회원입니다", null);
        }else{
            return ResponseUtil.SUCCESS(result[1],result[0]);
        }

    }

    //탈퇴하기
    @GetMapping("/withdraw")
    public ResponseEntity<String> signOut(@RequestBody WithdrawRequest withdrawRequest) {
        String response = userService.withDraw(withdrawRequest);
        return ResponseEntity.ok(response);
    }

}
