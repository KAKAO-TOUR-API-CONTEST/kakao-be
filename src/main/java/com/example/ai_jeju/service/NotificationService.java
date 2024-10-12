package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.example.ai_jeju.repository.ChatRoomRepository;
import com.example.ai_jeju.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private static final int INTERVAL = 3;

    private final EmitterRepository emitterRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public SseEmitter subscribeToRoom(Long userId, String roomId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, emitter);

        log.info("User with ID {} subscribed to room {}", userId, roomId);

        try {
            emitter.send(SseEmitter.event().name("subscription").data("sucscription"));
        } catch (IOException e) {
            log.error("Error sending subscription success message to user with ID {}", userId, e);
            emitter.completeWithError(e);
        }


        scheduler.scheduleAtFixedRate(() -> resendLastMessage(roomId, userId), 0, INTERVAL, TimeUnit.SECONDS);

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

    private void resendLastMessage(String roomId, Long userId) {
        SseEmitter emitter = emitterRepository.findByUserId(userId);
        if (emitter != null) {
            Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findByRoomId(roomId);
            if (chatRoomOpt.isPresent()) {
                ChatRoom chatRoom = chatRoomOpt.get();
                Optional<ChatMessage> lastMessage = chatMessageRepository.findTop1ByChatRoomOrderByIdDesc(chatRoom);
                if (lastMessage.isPresent()) {
                    try {
                        String message = lastMessage.get().getMessage();
                        log.info("Resending last message '{}' to user {} in room {}", message, userId, roomId);
                        emitter.send(SseEmitter.event().name("chatMessage").data(message));
                    } catch (IOException e) {
                        log.error("Error resending last message to user with ID {}", userId, e);
                        emitter.completeWithError(e);
                    }
                } else {
                    log.warn("No last message found for chat room {}", roomId);
                }
            } else {
                log.warn("No chat room found with ID {}", roomId);
            }
        }
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
