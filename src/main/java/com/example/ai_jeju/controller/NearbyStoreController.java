package com.example.ai_jeju.controller;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<List<Store>> getNearbyStores(@PathVariable("storeId")Long storeId) {
        List<Store> nearbyStores = storeService.getNearbyStores(storeId);
        return new ResponseEntity<>(nearbyStores, HttpStatus.OK);
    }
}
