package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.ChatMessage;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomId(String roomId);
    List<ChatMessage> findTop10ByRoomIdOrderByIdDesc(String roomId);

    List<ChatMessage> findTop10ByRoomIdAndIdLessThanOrderByIdDesc(String roomId, Long id);
}