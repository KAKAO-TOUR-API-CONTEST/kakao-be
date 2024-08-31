package com.example.ai_jeju.dto;


import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    public enum MessageType {
        TALK, EXIT, ENTER;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String profileImg;
}