package com.example.ai_jeju.controller;


import com.example.ai_jeju.domain.Emergency;
import com.example.ai_jeju.domain.Test;
import com.example.ai_jeju.domain.Test2;
import com.example.ai_jeju.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/tests")
    public String getAllTests(Model model) {
        List<Test2> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "imgtest"; // This will map to testImages.html in the templates folder
    }

    @GetMapping("/sse-test")
    public String sseTestPage() {
        return "test"; // test.html 파일을 반환
    }
}
