package com.example.ai_jeju.config;

import com.example.ai_jeju.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderCheckInterceptor implements HandlerInterceptor {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 예시로 "X-Custom-Header"라는 헤더를 확인
        // "X-Custom-Header" 확인
        String customHeader = request.getHeader("X-Custom-Header");
        if (customHeader != null) {
            System.out.println(customHeader);
        }

        //String headerValue = request.getHeader("access-token");

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 제거
        String token = getAccesToken(authorizationHeader);

        //System.out.println(token);
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증정보 설정한다.
        System.out.println("토큰 유효성 검사"+tokenProvider.validToken(token));

        // 헤더가 올바르면 요청을 진행
        return true;
    }

    private String getAccesToken(String authorizationHeader){
        System.out.println("get Access Token 실행");
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            System.out.println("get Access Token : " + authorizationHeader.substring(TOKEN_PREFIX.length()));
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        } else{
            System.out.println("Access Token 없음");
        }
        return null;
    }
}
