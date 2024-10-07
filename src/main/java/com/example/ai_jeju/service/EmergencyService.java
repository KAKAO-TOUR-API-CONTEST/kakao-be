package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Emergency;
import com.example.ai_jeju.repository.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyService {

    @Autowired EmergencyRepository emergencyRepository;

    public List<Emergency> getHospitals(){
        return emergencyRepository.findByCategory("병원");
    }

    public List<Emergency> getPharmacy(){
        return emergencyRepository.findByCategory("약국");
    }

    public Emergency getFacilityById(Long id) {
        return emergencyRepository.findById(id).orElse(null);
    }
}