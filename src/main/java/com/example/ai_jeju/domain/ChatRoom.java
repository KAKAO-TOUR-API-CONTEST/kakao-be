package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ChatRoom {

    @Id
    private String roomId;

    private String name;
    private String description;
    private String imgurl;

    // 기본 생성자
    public ChatRoom() {
    }

    // 이름과 roomId를 인자로 받는 생성자
    public ChatRoom(String roomId, String name, String description, String imgurl) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.imgurl = imgurl;
    }

    public static ChatRoom create(String roomId, String name, String description, String imgurl) {
        return new ChatRoom(roomId, name, description, imgurl);
    }
}