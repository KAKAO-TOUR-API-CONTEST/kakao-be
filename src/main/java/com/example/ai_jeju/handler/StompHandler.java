package com.example.ai_jeju.handler;

import com.example.ai_jeju.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final TokenProvider tokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";
    private final ConcurrentMap<String, Integer> roomUserCount = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ConcurrentMap<String, Integer>> userRoomEntryCount = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("preSend method called");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // WebSocket 연결 요청 처리
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken == null || !jwtToken.startsWith(BEARER_PREFIX)) {
                log.error("Authorization header is missing or does not start with 'Bearer '");
                throw new IllegalArgumentException("Invalid or missing Authorization header");
            }

            String token = jwtToken.substring(BEARER_PREFIX.length());
            log.info("Extracted token: {}", token);

            if (token.isEmpty()) {
                log.error("JWT token is empty");
                throw new IllegalArgumentException("JWT token is empty");
            }

            boolean isValid = tokenProvider.validToken(token);
            if (isValid) {
                log.info("JWT Token is valid. Proceeding with connection.");

                // 토큰에서 nickname 추출
                String nickname = tokenProvider.extractNickname(token);

                String profileImg = tokenProvider.extractProfileImg(token);
                log.info("Extracted nickname: {}", nickname);

                // WebSocket 세션에 nickname 저장
                accessor.getSessionAttributes().put("nickname", nickname);
                accessor.getSessionAttributes().put("profileImg", profileImg);
                accessor.getSessionAttributes().put("connected", true); // 연결 상태 저장
                accessor.getSessionAttributes().put("enter", false); // 초기에는 false로 설정


            } else {
                log.error("Invalid JWT Token");
                throw new IllegalArgumentException("Invalid JWT Token provided");
            }
        }

        // 메시지 전송 요청 처리
        else if (StompCommand.SEND == accessor.getCommand()) {
            String destination = accessor.getDestination();

            // 메시지의 목적지가 "/pub/chat/message"인지 확인
            if ("/pub/chat/message".equals(destination)) {
                String messageBody = new String((byte[]) message.getPayload());
                log.info("Message body: {}", messageBody);

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(messageBody);

                    String roomId = jsonNode.get("roomId").asText();
                    String messageType = jsonNode.get("type").asText();

                    // 토큰에서 가져온 nickname을 sender로 사용
                    String sender = (String) accessor.getSessionAttributes().get("nickname");
                    log.info("Sender retrieved from session: {}", sender);

                    if ("ENTER".equals(messageType)) {
                        log.info("User '{}' has entered room '{}'", sender, roomId);

                        // 사용자가 방에 입장할 때마다 방의 사용자 수 증가
                        roomUserCount.merge(roomId, 1, Integer::sum);
                        log.info("Room '{}' user count: {}", roomId, roomUserCount.get(roomId));

                        // 사용자 방 입장 횟수 증가
                        userRoomEntryCount
                                .computeIfAbsent(sender, k -> new ConcurrentHashMap<>())
                                .merge(roomId, 1, Integer::sum);
                        log.info("User '{}' entry count for room '{}': {}", sender, roomId, userRoomEntryCount.get(sender).get(roomId));

                        // 사용자가 실제로 ENTER 했음을 표시
                        accessor.getSessionAttributes().put("enter", true);

                        accessor.getSessionAttributes().put("roomId", roomId);
                    }

                    // 메시지 전송 시 sender를 설정
                    if ("TALK".equals(messageType)) {
                        log.info("User '{}' sent message to room '{}': {}", sender, roomId, jsonNode.get("message").asText());
                        // 메시지에 sender 추가하여 브로드캐스트
                        // messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, updatedMessage);
                    }

                } catch (Exception e) {
                    log.error("Error parsing message body", e);
                }
            }
        }

        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = accessor.getSessionId();
            log.info("DISCONNECT command received for session: {}", sessionId);

            Boolean connected = (Boolean) accessor.getSessionAttributes().get("connected");
            Boolean entered = (Boolean) accessor.getSessionAttributes().get("enter");

            if (connected != null && connected && entered != null && entered) {
                String nickname = (String) accessor.getSessionAttributes().get("nickname");
                String roomId = (String) accessor.getSessionAttributes().get("roomId");  // 세션에서 roomId를 가져옴

                if (nickname != null && roomId != null) {
                    Integer count = userRoomEntryCount.getOrDefault(nickname, new ConcurrentHashMap<>()).get(roomId);
                    if (count != null && count > 0) {
                        roomUserCount.merge(roomId, -1, Integer::sum);
                        userRoomEntryCount.get(nickname).merge(roomId, -1, Integer::sum);
                        log.info("User '{}' has left room '{}'. Updated user count: {}", nickname, roomId, roomUserCount.get(roomId));

                        // 마지막 입장인 경우 방 목록에서 제거
                        if (userRoomEntryCount.get(nickname).get(roomId) == 0) {
                            userRoomEntryCount.get(nickname).remove(roomId);
                        }
                    }
                }

                accessor.getSessionAttributes().put("connected", false);
            } else {
                log.warn("DISCONNECT called but user was not marked as connected or had not entered.");
            }
        }

        return message;
    }

    public int getUserCount(String roomId) {
        return roomUserCount.getOrDefault(roomId, 0);
    }
}
