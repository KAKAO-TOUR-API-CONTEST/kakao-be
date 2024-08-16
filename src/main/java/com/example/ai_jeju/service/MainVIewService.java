package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Restaurant;
import com.example.ai_jeju.domain.Stay;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.repository.RestaurantRepository;
import com.example.ai_jeju.repository.StayRepository;
import com.example.ai_jeju.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MainVIewService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private StoreRepository storeRepository;


    public Optional<Store> getDetailList(Long storeId){
        return storeRepository.findById(storeId);
    }

    public List<Store> getMainList(){
        return storeRepository.findAllOrderByRandomNative();
    }

        public List<Store>getListByCategory(int categoryId){
        return storeRepository.findByCategoryId(categoryId);
    }

     public List<Store>getListBySearch(String keyword){
        return storeRepository.findBySearch(keyword);
    }

    public List<Store>searchByCategory(String keyword, int categoryId){
        return storeRepository.findByKeywordAndCategoryId(keyword,categoryId);
    }



}
