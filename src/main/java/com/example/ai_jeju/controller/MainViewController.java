package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.service.MainVIewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainViewController {

    @Autowired
    private MainVIewService mainViewService;

    @GetMapping("/detailList")
    public Optional<Store> getUserById(@RequestParam Long storeId) {
        return mainViewService.getDetailList(storeId);
    }

    @GetMapping("/mainList")
    public List<Store> getMainLInst() {
        return mainViewService.getMainList();
    }

    @GetMapping("/mainList/filters")
    public List<Store> getListByCategory(@RequestParam int categoryId){
        return mainViewService.getListByCategory(categoryId);
    }

    @GetMapping("/searchList")
    public List<Store> getListBySearch(@RequestParam String keyword){
        return mainViewService.getListBySearch(keyword);
    }

    @GetMapping("/searchList/filters")
    public List<Store> searchByCategory(@RequestParam String keyword, @RequestParam int categoryId){
        return mainViewService.searchByCategory(keyword,categoryId);
    }


//    @GetMapping("/stay/detailList")
//    public Stay getStayById(@RequestParam int stayId) {
//        return mainViewService.getStayList(stayId);
//    }
//
//
//    @GetMapping("/play/detailList")
//    public Stay getStayById(@RequestParam int stayId) {
//        return mainViewService.getStayList(stayId);
//    }

}
