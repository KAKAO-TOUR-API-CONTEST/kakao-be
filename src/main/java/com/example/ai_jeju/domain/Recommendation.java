package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Recommendation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long recommendId;
}