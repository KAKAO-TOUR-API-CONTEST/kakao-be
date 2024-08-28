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


    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init() {
        if (chatRoomRepository.count() == 0) {
            chatRoomRepository.save(new ChatRoom("1", "설렘 가득한 여행 준비", "제주 여행 계획을 짜고있는 엄마아빠들과 소통해요", "https://ai-jeju.s3.ap-northeast-2.amazonaws.com/%EC%9D%B4%EB%AF%B8%EC%A7%801.png"));
            chatRoomRepository.save(new ChatRoom("2", "즐거운 제주 여행", "제주 여행을 즐기고 있는 엄마아빠들과 소통해요", "https://ai-jeju.s3.ap-northeast-2.amazonaws.com/%EC%9D%B4%EB%AF%B8%EC%A7%802.png"));
            chatRoomRepository.save(new ChatRoom("3", "제주에서 새로운 인연", "아이제주를 통해 새로운 인연을 만들어요", "https://ai-jeju.s3.ap-northeast-2.amazonaws.com/%EC%9D%B4%EB%AF%B8%EC%A7%803.jpg"));
        }
    }

    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElse(null);
    }

    public List<ChatMessage> getAllMessages(ChatRoom chatRoom) {
        return chatMessageRepository.findByChatRoomOrderByIdAsc(chatRoom);
    }

    public List<ChatMessage> previousMessages(ChatRoom chatRoom, Long lastMessageId) {
        if (lastMessageId == null) {
            // 마지막 메시지 ID가 없으면 해당 채팅방의 최신 메시지 10개 가져오기
            return chatMessageRepository.findTop10ByChatRoomOrderByIdDesc(chatRoom);
        }
        // 특정 ID 이전의 해당 채팅방 메시지 10개 가져오기
        return chatMessageRepository.findTop10ByChatRoomAndIdLessThanOrderByIdDesc(chatRoom, lastMessageId);
    }

    public ChatMessage findLastMessage(ChatRoom chatRoom) {
        return chatMessageRepository.findTop1ByChatRoomOrderByIdDesc(chatRoom).orElse(null);
    }
}