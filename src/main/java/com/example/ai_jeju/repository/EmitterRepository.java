package com.example.ai_jeju.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {
    // 유저별 Emitter 저장 (roomId -> userId -> SseEmitter)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public SseEmitter findByUserId(Long userId) {
        return emitters.get(userId);
    }

    public void deleteByUserId(Long userId) {
        emitters.remove(userId);
    }

    public List<Long> findAllUserIds() {
        return emitters.keySet().stream().collect(Collectors.toList());
    }
}
