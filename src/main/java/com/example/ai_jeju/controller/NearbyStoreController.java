package com.example.ai_jeju.controller;


import com.example.ai_jeju.domain.NearbyStore;
import com.example.ai_jeju.service.NearbyStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class NearbyStoreController {

    @Autowired
    private NearbyStoreService nearbyStoreService;

    // storeId로 근처 매장 3개 가져오기
    @GetMapping("/{storeId}/nearby")
    public ResponseEntity<List<NearbyStore>> getNearbyStores(@PathVariable("storeId") Long storeId) {
        List<NearbyStore> nearbyStores = nearbyStoreService.getNearbyStores(storeId);
        return ResponseEntity.ok(nearbyStores);
    }

}
