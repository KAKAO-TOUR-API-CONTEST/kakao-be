package com.example.ai_jeju.controller;

import com.example.ai_jeju.dto.RecommendRequest;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class RecommendationController {

    private final TokenProvider tokenProvider;

    @Autowired
    private RecommendationService recommendationService;

    public RecommendationController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/recommendsave")
    public ResponseEntity<String> saveRecommendations(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody RecommendRequest recommendRequest) {
        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    recommendationService.saveRecommendations(userId, recommendRequest.getRecommendations());
                    return ResponseEntity.ok("선택ID저장");
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 데이터: " + e.getMessage());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("저장실패: " + e.getMessage());
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지않은토큰");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰없음");
        }
    }
}