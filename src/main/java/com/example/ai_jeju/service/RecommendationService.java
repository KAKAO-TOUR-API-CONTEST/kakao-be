package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.Recommendation;
import com.example.ai_jeju.repository.RecommendationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Transactional // 트랜잭션 처리 추가
    public void saveRecommendations(Long userId, List<List<Long>> recommendIdsList) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (List<Long> recommendIds : recommendIdsList) {
            for (Long recommendId : recommendIds) {
                recommendations.add(Recommendation.builder()
                        .userId(userId)
                        .recommendId(recommendId)
                        .build());
            }
        }
        recommendationRepository.saveAll(recommendations);  // 성능 개선을 위해 한번에 저장
    }
}
