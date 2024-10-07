package com.example.ai_jeju.repository;


import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepository {

    private final Map<Long, Map<Long, SseEmitter>> emittersByRoom = new ConcurrentHashMap<>();


    public void save(Long userId, Long roomId, SseEmitter emitter) {
        emittersByRoom.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(userId, emitter);
    }

    public Map<Long, SseEmitter> findAllEmittersByRoomId(Long roomId) {
        return emittersByRoom.getOrDefault(roomId, Collections.emptyMap());
    }

    public void deleteByRoomId(Long userId, Long roomId) {
        Map<Long, SseEmitter> roomEmitters = emittersByRoom.get(roomId);
        if (roomEmitters != null) {
            roomEmitters.remove(userId);
        }
    }
}
