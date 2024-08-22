package com.example.ai_jeju.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String name;

    // 기본 생성자
    public ChatRoom() {
    }

    // 이름과 roomId를 인자로 받는 생성자
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static ChatRoom create(String roomId, String name) {
        return new ChatRoom(roomId, name);
    }
}
