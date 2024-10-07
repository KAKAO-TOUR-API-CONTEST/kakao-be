package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.Emergency;
import com.example.ai_jeju.domain.Test;
import com.example.ai_jeju.domain.Test2;
import com.example.ai_jeju.repository.EmergencyRepository;
import com.example.ai_jeju.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public List<Test2> getAllTests() {
        return testRepository.findAll();
    }


}
