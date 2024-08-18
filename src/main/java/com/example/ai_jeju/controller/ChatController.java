package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.example.ai_jeju.repository.ChatRoomRepository;
import com.example.ai_jeju.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto messageDto) {
        if (messageDto == null) {
            log.error("Received null message");
            return;
        }

        log.info("Received message: " + messageDto.toString());

        // 메시지 타입에 따라 처리
        switch (messageDto.getType()) {
            case ENTER:
                messageDto.setMessage(messageDto.getSender() + "님이 입장하셨습니다.");
                break;
            case EXIT:
                messageDto.setMessage(messageDto.getSender() + "님이 퇴장하셨습니다.");
                break;
            default:
                break;
        }

        // 메시지 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .roomId(messageDto.getRoomId())
                .sender(messageDto.getSender())
                .message(messageDto.getMessage())
                .timestamp(LocalDateTime.now())
                .type(messageDto.getType().name())
                .build();
        chatMessageRepository.save(chatMessage);

        log.info("Sending message to /sub/chat/room/" + messageDto.getRoomId() + ": " + messageDto.toString());
        messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
    }

    @GetMapping("/chatroom")
    public String chat(@RequestParam("roomId") String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chat"; // chat.html 파일을 반환
    }

    @GetMapping("/chat/previous")
    @ResponseBody
    public List<ChatMessage> getPreviousMessages(
            @RequestParam("roomId") String roomId,
            @RequestParam(value = "lastMessageId", required = false) Optional<Long> lastMessageId) {
        return chatService.previousMessages(roomId, lastMessageId.orElse(null));
    }





}