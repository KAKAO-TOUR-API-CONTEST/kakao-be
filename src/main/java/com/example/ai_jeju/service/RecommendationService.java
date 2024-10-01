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

    @Transactional
    public void saveRecommendations(Long userId, List<Long> restaurantIds, List<Long> leisureIds, List<Long> hotelIds) {
        List<Recommendation> recommendations = new ArrayList<>();

        // 음식점 리스트 저장
        for (Long restaurantId : restaurantIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(restaurantId)
                    .build());
        }

        // 레저 리스트 저장
        for (Long leisureId : leisureIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(leisureId)
                    .build());
        }

        // 호텔 리스트 저장
        for (Long hotelId : hotelIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(hotelId)
                    .build());
        }

        recommendationRepository.saveAll(recommendations);  // 성능 개선을 위해 한 번에 저장
    }
}