package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.example.ai_jeju.repository.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;


    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init() {
        // 데이터베이스에 이미 채팅방이 존재하는지 확인
        if (chatRoomRepository.count() == 0) {
            // 방 3개를 고정적으로 생성하여 데이터베이스에 저장
            chatRoomRepository.save(new ChatRoom("1", "Room 1"));
            chatRoomRepository.save(new ChatRoom("2", "Room 2"));
            chatRoomRepository.save(new ChatRoom("3", "Room 3"));
        }
    }

    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElse(null);
    }

    public List<ChatMessage> getAllMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByIdAsc(roomId);
    }

    public List<ChatMessage> previousMessages(String roomId, Long lastMessageId) {
        if (lastMessageId == null) {
            // 마지막 메시지 ID가 없으면 해당 채팅방의 최신 메시지 10개 가져오기
            return chatMessageRepository.findTop10ByRoomIdOrderByIdDesc(roomId);
        }
        // 특정 ID 이전의 해당 채팅방 메시지 10개 가져오기
        return chatMessageRepository.findTop10ByRoomIdAndIdLessThanOrderByIdDesc(roomId, lastMessageId);
    }
}
