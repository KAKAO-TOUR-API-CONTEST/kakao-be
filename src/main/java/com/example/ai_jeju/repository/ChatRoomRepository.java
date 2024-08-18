package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();

        // RoomId를 1, 2, 3으로 설정하여 방 생성
        chatRoomMap.put("1", new ChatRoom("1", "Room 1"));
        chatRoomMap.put("2", new ChatRoom("2", "Room 2"));
        chatRoomMap.put("3", new ChatRoom("3", "Room 3"));
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }
}