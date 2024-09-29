package com.example.ai_jeju.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import software.amazon.ion.Timestamp;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp timestamp;
}