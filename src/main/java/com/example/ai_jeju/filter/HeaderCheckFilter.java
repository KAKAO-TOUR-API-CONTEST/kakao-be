package com.example.ai_jeju.filter;

import java.io.IOException;

import com.example.ai_jeju.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

public class HeaderCheckFilter extends OncePerRequestFilter {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 예시로 "X-Custom-Header"라는 헤더를 확인
        String headerValue = request.getHeader("X-Custom-Header");
        if (headerValue == null || !headerValue.equals("ExpectedValue")) {
            // 헤더가 없거나 예상된 값이 아닐 경우 요청을 차단
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing or Invalid Header");
            return;
        }
        // 헤더가 올바르면 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}
