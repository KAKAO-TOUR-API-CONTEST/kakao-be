package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private ChatRoom chatRoom;

    private String sender;
    private String message;
    private String type;
    @UpdateTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    private String profileImg;
}