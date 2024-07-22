package com.example.ai_jeju.controller;


import com.example.ai_jeju.domain.Restaurant;
import com.example.ai_jeju.domain.Stay;
import com.example.ai_jeju.service.MainVIewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainViewController {

    @Autowired
    private MainVIewService mainViewService;

    /*
    @GetMapping("/mainList")
    public ResponseEntity<String> signOut(@RequestBody WithdrawRequest withdrawRequest) {
        String response = userService.withDraw(withdrawRequest);

        return ResponseEntity.ok(response);
    }*/

    /*
    @GetMapping("/mainList")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    */

    @GetMapping("/restaurant/detailList")
    public Restaurant getUserById(@RequestParam int restaurantId) {
        return mainViewService.getRestaurantList(restaurantId);
    }


    @GetMapping("/stay/detailList")
    public Stay getStayById(@RequestParam int stayId) {
        return mainViewService.getStayList(stayId);
    }

}
