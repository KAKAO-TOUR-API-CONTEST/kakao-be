package com.example.ai_jeju.controller;


import com.example.ai_jeju.domain.Emergency;
import com.example.ai_jeju.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @GetMapping("/hospitals")
    public List<Emergency> getAllHospitals() {
        return emergencyService.getHospitals();
    }

    @GetMapping("/pharmacies")
    public List<Emergency> getAllPharmacies() {
        return emergencyService.getPharmacy();
    }

    @GetMapping("/facilities/{emergencyid}")
    public Emergency getFacilityById(@PathVariable("emergencyid") Long emergencyid) {
        return emergencyService.getFacilityById(emergencyid);
    }




}
