package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="stores")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId", updatable = false, unique = true)
    private Long storeId;

    @Column(name = "name",updatable = false)
    String name;

    //이미지 소스
    @Column(name = "imgSrc",updatable = false)
    boolean imgSrc;

    @Column(name = "address",updatable = false)
    String address;


    @Column(name = "mapX",updatable = false)
    double mapX;

    @Column(name = "mapY",updatable = false)
    double mapY;




    /**
     카테고리 value
     1 : 숙박
     2 : 음식점
     3 : 레져
     */

    //유모차 대여여부
    @Column(name = "stroller",updatable = false)
    boolean stroller;

    //유모차 편의성
    @Column(name = "strollerVal",updatable = false)
    int strollerVal;

    //아이 스페어 체어
    @Column(name = "babySpareChair",updatable = false)
    boolean babySpareChair;

    //아이 놀이방
    @Column(name = "playground",updatable = false)
    boolean playground;

    //노키즈존 여부
    @Column(name = "noKidsZone",updatable = false)
    boolean noKidsZone;

    @Column(name = "categoryId",updatable = false)
    int categoryId;

    @Column(name = "operationTime",updatable = false)
    String operationTime;

    @Column(name = "tel",updatable = false)
    String tel;

}
