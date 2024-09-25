package com.example.ai_jeju.controller;


import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NearbyStoreController {

    @Autowired
    private StoreService storeService;


    @GetMapping("/{storeId}/nearby")
    public ResponseEntity<List<Store>> getNearbyStores(@PathVariable("storeId") Long storeId) {
        // 3. 서비스에서 근처 가게 정보를 가져와서 반환
        List<Store> nearbyStores = storeService.getNearbyStores(storeId);
        return ResponseEntity.ok(nearbyStores);
    }
}
