package com.example.ai_jeju.handler;

import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("preSend method called");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) { // WebSocket 연결 요청
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken == null) {
                log.error("Authorization header is missing");
            } else {
                log.info("Authorization header present: {}", jwtToken);
                String token = jwtToken.substring(7);
                log.info("Extracted token: {}", token);

                boolean isValid = tokenProvider.validToken(token);
                if (isValid) {
                    log.info("JWT Token is valid. Proceeding with connection.");

                    // 토큰에서 nickname 추출
                    String nickname = tokenProvider.extractNickname(token);
                    log.info("Extracted nickname: {}", nickname);

                    // WebSocket 세션에 nickname 저장
                    accessor.getSessionAttributes().put("nickname", nickname);
                } else {
                    log.error("Invalid JWT Token");
                    throw new IllegalArgumentException("Invalid JWT Token provided");
                }
            }
        }

        return message;
    }
}
