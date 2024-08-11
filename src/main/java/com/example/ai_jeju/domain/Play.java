package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="play")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Play {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playId", updatable = false, unique = true)
    int playId;

    @Column(name = "playName", updatable = false)
    String playName;

    @Column(name = "latitude", updatable = false)
    double latitude;

    @Column(name = "longitude", updatable = false)
    double longitude;

    @Column(name = "addr", updatable = false)
    String address;

    @Column(name = "dc", updatable = false)
    int chair;

    @Column(name = "nokidsZone", updatable = false)
    Boolean nokidsZone;

    @Column(name = "operation_time", updatable = false)
    int operationTime;
}
