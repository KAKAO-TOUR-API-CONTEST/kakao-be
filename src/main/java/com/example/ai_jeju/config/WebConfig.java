package com.example.ai_jeju.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HeaderCheckInterceptor headerCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 모든 경로에 대해 인터셉터를 적용 (필요에 따라 경로를 지정할 수 있음)
        registry.addInterceptor(headerCheckInterceptor).addPathPatterns("/**");
    }
}
