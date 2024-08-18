package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="stay")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stay_id", updatable = false, unique = true)
    int stayId;

    @Column(name = "name", updatable = false, unique = true)
    String name;

    @Column(name = "mapX", updatable = false, unique = true)
    double mapX;

    @Column(name = "mapY", updatable = false, unique = true)
    double mapY;

    @Column(name = "address", updatable = false, unique = true)
    String address;

    @Column(name = "bed_forchild", updatable = false, unique = true)
    int chair;

    @Column(name = "stroller", updatable = false, unique = true)
    int stroller;

    @Column(name = "operation_time", updatable = false, unique = true)
    int operationTime;


}
