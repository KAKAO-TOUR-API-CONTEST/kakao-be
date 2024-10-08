package com.example.ai_jeju.service;

import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 1시간 타임아웃

    private final EmitterRepository emitterRepository;

    public SseEmitter subscribeToRoom(Long userId) {
        SseEmitter emitter = new SseEmitter((DEFAULT_TIMEOUT));

        emitterRepository.save(userId, emitter);

        log.info("User with ID {} subscribed to room", userId);

        try {
            emitter.send(SseEmitter.event().name("subscription").data("subscription"));
        } catch (IOException e) {
            log.error("Error sending subscription success message to user with ID {}", userId, e);
            emitter.completeWithError(e);
        }

        emitter.onCompletion(() -> {
            log.info("SSE connection completed for user {}", userId);
            emitterRepository.deleteByUserId(userId);
        });

        emitter.onTimeout(() -> {
            log.warn("SSE connection timed out for user {}", userId);
            emitterRepository.deleteByUserId(userId);
        });

        return emitter;
    }

    public void sendMessage(Long userId, String message) {
        SseEmitter emitter = emitterRepository.findByUserId(userId);

        if (emitter != null) {
            try {
                log.info("Sending message '{}' to user with ID {}", message, userId);
                emitter.send(SseEmitter.event().name("chatMessage").data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }

    // 특정 사용자에게 이벤트를 알리는 메소드
    public void notify(Long userId, Object event) {
        sendMessage(userId, event.toString());
    }

    public void notifyAllSubscribers(ChatMessageDto messageDto) {
        List<Long> allUserIds = emitterRepository.findAllUserIds();
        for (Long userId : allUserIds) {
            sendMessage(userId, messageDto.getMessage());
        }
    }

}
