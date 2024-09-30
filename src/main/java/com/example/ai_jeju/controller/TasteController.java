package com.example.ai_jeju.controller;

import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.StoreRepositoryCustomImpl;
import com.example.ai_jeju.service.SpecialRcmdService;
import com.example.ai_jeju.service.TasteService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TasteController {
    private final TokenProvider tokenProvider;
    @Autowired
    private StoreRepositoryCustomImpl storeRepositoryCustom;
    @Autowired
    private TasteService tasteService;

    public TasteController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/taste")
    ResponseDto getTasteOptions(@RequestParam("category") int category, @RequestParam("randomSeed") int randomSeed) {
        return ResponseUtil.SUCCESS("", tasteService.getTasteOptions(category, randomSeed));
    }

}
