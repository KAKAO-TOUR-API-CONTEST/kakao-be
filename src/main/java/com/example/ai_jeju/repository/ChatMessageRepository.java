package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // ChatRoom 객체를 기반으로 메시지를 가져옵니다.
    List<ChatMessage> findByChatRoomOrderByIdAsc(ChatRoom chatRoom);

    List<ChatMessage> findTop10ByChatRoomOrderByIdDesc(ChatRoom chatRoom);

    List<ChatMessage> findTop10ByChatRoomAndIdLessThanOrderByIdDesc(ChatRoom chatRoom, Long id);

    Optional<ChatMessage> findTop1ByChatRoomOrderByIdDesc(ChatRoom chatRoom);
}