package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Restaurant;
import com.example.ai_jeju.domain.Stay;
import com.example.ai_jeju.repository.MainListRepository;
import com.example.ai_jeju.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MainVIewService {

    @Autowired
    private MainListRepository mainListRepository;
    @Autowired
    private StayRepository stayRepository;

    public Restaurant getRestaurantList(int restaruantId){
        return mainListRepository.findByRestarauntId(restaruantId)
                .orElseThrow(
                        ()-> new IllegalArgumentException("unexpected restaurant"));
    }

    public Stay getStayList(int stayId){
        return stayRepository.findByStayId(stayId)
                .orElseThrow(
                        ()-> new IllegalArgumentException("unexpected stay"));
    }


}
