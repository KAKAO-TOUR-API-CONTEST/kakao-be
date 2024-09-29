package com.example.ai_jeju.dto;

import java.util.List;

public class RecommendRequest {

    private List<List<Long>> recommendations;

    // Getter and Setter
    public List<List<Long>> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<List<Long>> recommendations) {
        this.recommendations = recommendations;
    }
}
