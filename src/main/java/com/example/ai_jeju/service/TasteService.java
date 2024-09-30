package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.MainListResponse;
import com.example.ai_jeju.dto.TasteOptionResponse;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.repository.StoreRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TasteService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreRepositoryCustomImpl storeRepositoryCustom;

    public List<TasteOptionResponse> getTasteOptions(int category, int randomSeed) {

        List<TasteOptionResponse> taseteOptionResponses = new ArrayList<>();

        List<Store> stores = storeRepositoryCustom.findTasteOption(category, randomSeed);
        List<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
        for (Store store : stores) {

            TasteOptionResponse tasteOptionResponse = TasteOptionResponse.builder()
                    .storeId(store.getStoreId())
                    .imgSrc(store.getImgSrc())
                    .name(store.getName())
                    .build();

            tasteOptionResponses.add(tasteOptionResponse);
        }
        return tasteOptionResponses;

    }

}
