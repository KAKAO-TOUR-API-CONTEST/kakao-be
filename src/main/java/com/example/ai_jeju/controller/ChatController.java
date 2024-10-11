package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.dto.ChatMessageDto;
import com.example.ai_jeju.handler.StompHandler;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChatMessageRepository;
import com.example.ai_jeju.repository.ChatRoomRepository;
import com.example.ai_jeju.service.ChatService;
import com.example.ai_jeju.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import software.amazon.ion.Timestamp;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final TokenProvider tokenProvider;
    private final StompHandler stompHandler;
    private final NotificationService notificationService;

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
            String profileImg = (String) sessionAttributes.get("profileImg");
            log.info("Received message: " + messageDto.toString());

            // sender가 null일 경우 세션에서 추출한 nickname을 sender로 설정
            if (messageDto.getSender() == null) {
                messageDto.setSender(nickname);
            }
            if (messageDto.getProfileImg() == null) {
                messageDto.setProfileImg(profileImg);
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

            // roomId를 사용해 ChatRoom 객체를 가져오기
            ChatRoom chatRoom = chatRoomRepository.findByRoomId(messageDto.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid roomId: " + messageDto.getRoomId()));

            // ENTER 타입이 아닌 경우에만 메시지를 저장하기
            if (messageDto.getType() != ChatMessageDto.MessageType.ENTER) {
                try {
                    ChatMessage chatMessage = ChatMessage.builder()
                            .chatRoom(chatRoom)
                            .sender(messageDto.getSender())
                            .message(messageDto.getMessage())
                            .profileImg(messageDto.getProfileImg())
                            .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                            .type(messageDto.getType().name())
                            .build();
                    log.info("Saving ChatMessage: " + chatMessage.toString());
                    chatMessageRepository.save(chatMessage);
                } catch (Exception e) {
                    log.error("Error saving ChatMessage", e);
                }
            }


            log.info("Sending message to /sub/chat/room/" + messageDto.getRoomId() + ": " + messageDto.toString());
            messagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomId(), messageDto);
            notificationService.notifyAllSubscribers(messageDto);

        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }


    @CrossOrigin(origins = "https://www.ijeju.shop/")
    @GetMapping(value = "/api/chat/{roomId}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToRoom(@PathVariable("roomId") Long roomId, @RequestParam(value = "token", required = false) String token) {
        if (token != null && !token.isEmpty()) {
            log.info("Received token: " + token);

            String accessToken = token.replace("Bearer ", "");
            log.info("Processed access token: " + accessToken);


            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                log.info("User ID extracted from token: " + userId);

                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                if (response != null) {
                    response.setHeader("X-Accel-Buffering", "no");
                    response.setContentType("text/event-stream;charset=UTF-8");
                }

                return notificationService.subscribeToRoom(userId);
            } else {
                log.error("Invalid token: " + accessToken);
                throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
            }
        } else {
            log.error("No token provided.");
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }



    @PostMapping("/send-data/{id}")
    public void sendData(@PathVariable("roomId") Long id) {
        notificationService.notify(id, "data");
    }

    @GetMapping(value = "/notifications/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable("roomId") Long userId) {
        return notificationService.subscribeToRoom(userId);
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

        // roomId를 사용해 ChatRoom 객체 가져오기
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomId: " + roomId));

        return chatService.previousMessages(chatRoom, lastMessageId.orElse(null));
    }

    @GetMapping("/chat/allprevious")
    @ResponseBody
    public List<ChatMessage> getAllMessages(@RequestParam("roomId") String roomId) {

        // roomId를 사용해 ChatRoom 객체를 가져오기
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomId: " + roomId));

        return chatService.getAllMessages(chatRoom);
    }

    @GetMapping("/chat/messagecount")
    @ResponseBody
    public int getMessageCount(@RequestParam("roomId") String roomId) {


        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomId: " + roomId));

        return chatService.getMessageCount(chatRoom);
    }

    @GetMapping("/chat/users/count")
    @ResponseBody
    public Map<String, Integer> getAllUserCounts() {
        // ChatRoomRepository에서 모든 roomId를 가져와 초기화
        List<String> roomIds = chatRoomRepository.findAllRoomIds();
        stompHandler.initializeRoomUserCount(roomIds);  // 모든 방의 사용자 수 초기화
        return new HashMap<>(stompHandler.getAllUserCounts());
    }




}