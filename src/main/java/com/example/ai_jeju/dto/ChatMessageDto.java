package com.example.ai_jeju.dto;


import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    public enum MessageType {
        ENTER, TALK, EXIT;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}