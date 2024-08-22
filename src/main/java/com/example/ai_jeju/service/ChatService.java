package com.example.ai_jeju.service;


import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Data
@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

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