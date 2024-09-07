package com.example.ai_jeju.controller;

import com.example.ai_jeju.service.ScheduleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleItemController {

    @Autowired
    private ScheduleItemService scheduleItemService;

//    @PostMapping("")
//    public void addSchedule

}
