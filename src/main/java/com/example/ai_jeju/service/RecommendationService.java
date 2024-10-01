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

        for (Long restaurantId : restaurantIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(restaurantId)
                    .build());
        }

        for (Long leisureId : leisureIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(leisureId)
                    .build());
        }


        for (Long hotelId : hotelIds) {
            recommendations.add(Recommendation.builder()
                    .userId(userId)
                    .recommendId(hotelId)
                    .build());
        }

        recommendationRepository.saveAll(recommendations);
    }
}