package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.ChatRoom;
import com.example.ai_jeju.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;


    @GetMapping("/rooms")
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }


    @GetMapping("/room/{roomId}")
    public ChatRoom roomInfo(@PathVariable("roomId") String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    @Controller
    @RequestMapping("/chat/chatrooms")
    public static class ChatRoomPageController {

        @GetMapping
        public String chatRooms() {
            return "chatrooms";
        }
    }
}
