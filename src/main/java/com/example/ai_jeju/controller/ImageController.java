package com.example.ai_jeju.controller;


import com.example.ai_jeju.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @GetMapping("/presignedurl")
    @ResponseBody
    String getURL(@RequestParam(name = "filename") String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
    }

    @GetMapping("/img")
    public String writePage() {
        return "write";
    }

}
