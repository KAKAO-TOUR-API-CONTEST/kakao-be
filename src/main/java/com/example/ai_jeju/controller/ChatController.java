package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.example.ai_jeju.repository.ChatRoomRepository;
import com.example.ai_jeju.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto messageDto, @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
        if (messageDto == null) {
            log.error("Received null message");
            return;
        }

        try {
            String nickname = (String) sessionAttributes.get("nickname");
            log.info("Received message: " + messageDto.toString());

            // sender가 null일 경우 세션에서 추출한 nickname을 sender로 설정
            if (messageDto.getSender() == null) {
                messageDto.setSender(nickname);
            }

            switch (messageDto.getType()) {
                case ENTER:
                    messageDto.setMessage(nickname + "님이 입장하셨습니다.");
                    break;
                case EXIT:
                    messageDto.setMessage(nickname + "님이 퇴장하셨습니다.");
                    break;
                default:
                    break;
            }

            ChatMessage chatMessage = ChatMessage.builder()
                    .roomId(messageDto.getRoomId())
                    .sender(messageDto.getSender())  // nickname이 sender로 설정됨
                    .message(messageDto.getMessage())
                    .timestamp(LocalDateTime.now())
                    .type(messageDto.getType().name())
                    .build();
            chatMessageRepository.save(chatMessage);

            log.info("Sending message to /sub/chat/room/" + messageDto.getRoomId() + ": " + messageDto.toString());
            messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
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