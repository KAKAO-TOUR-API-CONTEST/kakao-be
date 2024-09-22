package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.NearbyStore;
import com.example.ai_jeju.repository.NearbyStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NearbyStoreService {
    @Autowired
    private NearbyStoreRepository nearbyStoreRepository;

    // storeId로 근처 매장 3개 가져오기
    public List<NearbyStore> getNearbyStores(Long storeId) {
        return nearbyStoreRepository.findByStoreId(storeId);
    }
}
