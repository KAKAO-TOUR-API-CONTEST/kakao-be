package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.ChatMessage;
import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.handler.StompHandler;
import com.example.ai_jeju.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;
    private final StompHandler stompHandler;

    @GetMapping("/rooms")
    public List<ChatRoom> room() {
        return chatService.findAllRooms();
    }

    @GetMapping("/room/{roomId}")
    public ChatRoom roomInfo(@PathVariable("roomId") String roomId) {
        return chatService.findRoomById(roomId);
    }

    @Controller
    @RequestMapping("/chat/chatrooms")
    public static class ChatRoomPageController {

        @GetMapping
        public String chatRooms() {
            return "chatrooms";
        }
    }

    @GetMapping("/room/{roomId}/lastmessage")
    public ChatMessage getLastMessage(@PathVariable("roomId") String roomId) {
        ChatRoom chatRoom = chatService.findRoomById(roomId);
        if (chatRoom == null) {
            throw new IllegalArgumentException("Invalid roomId: " + roomId);
        }
        return chatService.findLastMessage(chatRoom);
    }


    @GetMapping("/users/{roomId}/count")
    public int getUserCount(@PathVariable("roomId") String roomId) {
        return stompHandler.getUserCount(roomId);
    }

    @GetMapping("/room/{roomId}/lastmessages")
    public List<ChatMessage> getLastTwoMessages(@PathVariable("roomId") String roomId) {
        ChatRoom chatRoom = chatService.findRoomById(roomId);
        if (chatRoom == null) {
            throw new IllegalArgumentException("Invalid roomId: " + roomId);
        }
        return chatService.findLastMessages(chatRoom, 2);
    }






}