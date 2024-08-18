package com.example.ai_jeju.domain;

import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ChatRoom {

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private String roomId;
    private String name;

    // 기본 생성자
    public ChatRoom() {
        this.roomId = String.valueOf(idCounter.incrementAndGet());
    }

    // 이름과 ID를 인자로 받는 생성자
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = String.valueOf(idCounter.incrementAndGet());
        chatRoom.name = name;
        return chatRoom;
    }
}