package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="restaurant")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Restaurant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaraunt_id", updatable = false, unique = true)
    int restarauntId;


    @Column(name = "name", updatable = false, unique = true)
    String name;

    @Column(name = "latitude", updatable = false, unique = true)
    double latitude;

    @Column(name = "longitude", updatable = false, unique = true)
    double longitude;

    @Column(name = "address", updatable = false, unique = true)
    String address;

    @Column(name = "chair", updatable = false, unique = true)
    int chair;

    @Column(name = "palyground", updatable = false, unique = true)
    int playground;

    @Column(name = "stroller", updatable = false, unique = true)
    int stroller;

    @Column(name = "operation_time", updatable = false, unique = true)
    int operationTime;


}
