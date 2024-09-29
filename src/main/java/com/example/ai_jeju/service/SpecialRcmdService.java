package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.MainListResponse;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialRcmdService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;


    public List<MainListResponse> getSpecialRcmd(int rcmdType, int category){
        List<MainListResponse> mainListResponses = new ArrayList<>();

        List<Store> stores = storeRepository.findByRcmdTypeAndCategoryId(rcmdType,category);
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

    public List<MainListResponse> getSpecialRcmd(int rcmdType, int category, Long userId){
        List<MainListResponse> mainListResponses = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        List<Store> stores = storeRepository.findByRcmdTypeAndCategoryId(rcmdType,category);
        for(Store store : stores){

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(store.getNoBmk())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }



}
