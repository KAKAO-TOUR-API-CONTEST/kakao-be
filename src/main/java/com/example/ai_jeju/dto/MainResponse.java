package com.example.ai_jeju.dto;

import com.example.ai_jeju.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MainResponse {

    List<MainListResponse> stores;
    Long endPage;
}
