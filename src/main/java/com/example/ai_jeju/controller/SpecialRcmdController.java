package com.example.ai_jeju.controller;


import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.MainVIewService;
import com.example.ai_jeju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpecialRcmdController {

    private final TokenProvider tokenProvider;
    @Autowired
    private MainVIewService mainViewService;
    @Autowired
    private UserService userService;

    public SpecialRcmdController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }






}
