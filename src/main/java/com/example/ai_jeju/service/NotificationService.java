package com.example.ai_jeju.service;

import com.example.ai_jeju.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 1시간 타임아웃

    private final EmitterRepository emitterRepository;

    // 채팅방에 대한 구독 처리
    public SseEmitter subscribeToRoom(Long userId, Long roomId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // 구독 정보 저장 (userId와 roomId로 구독 관리)
        emitterRepository.save(userId, roomId, emitter);

        try {
            // 구독이 성공적으로 이루어졌음을 알리는 메시지 전송
            emitter.send(SseEmitter.event().name("subscription").data("채팅방 구독 성공: " + roomId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        // Emitter가 완료되거나 타임아웃될 때 처리
        emitter.onCompletion(() -> emitterRepository.deleteByRoomId(userId, roomId));
        emitter.onTimeout(() -> emitterRepository.deleteByRoomId(userId, roomId));

        return emitter;
    }

    // 특정 채팅방에 메시지 전송
    public void sendMessageToRoom(Long roomId, String message) {
        Map<Long, SseEmitter> emitters = emitterRepository.findAllEmittersByRoomId(roomId);

        // 방에 연결된 모든 구독자에게 메시지 전송
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("chatMessage").data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
    }
}
