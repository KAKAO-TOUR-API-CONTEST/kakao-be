package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.MainListResponse;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialRcmdService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private BookmarkRepository bookmarkRepositoryRepository;

    public List<MainListResponse> getSpecialRcmd(int rcmdType, int category){
        List<MainListResponse> mainListResponses = new ArrayList<>();

        List<Store> stores = storeRepository.findByCategoryIdAndRcmdType(rcmdType,category);
        for(Store store : stores){

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(store.getNoBmk())
                    .bmkSatus(false)
                    .build();
            mainListResponses.add(mainListResponse);
        }
       return mainListResponses;
    }



}
