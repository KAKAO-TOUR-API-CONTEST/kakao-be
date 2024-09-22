package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "NearbyStores")
@Getter
@Setter
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class NearbyStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", updatable = false, unique = true)
    private Long Id;

    private Long storeId;
    private Long nearbyStoreId;
    private String name;
    private String address;
    private String tel;
    private Long categoryId;
    private Boolean playground;
    private Boolean stroller;
    @Column(name = "strollerVal",updatable = false,nullable = true)
    Boolean strollerVal;

    @Column(name = "noKidsZone",updatable = false,nullable = true)
    String noKidsZone;

    @Column(name = "nokidsdetail",updatable = false,nullable = true)
    String nokidsdetail;

    @Column(name = "pet",updatable = false,nullable = true)
    Boolean pet;

    @Column(name = "parking",updatable = false,nullable = true)
    Boolean parking;

    @Column(name = "babySpareChair",updatable = false,nullable = true)
    Boolean babySpareChair;

    @Column(name = "mapX",updatable = false,nullable = true)
    Double mapX;

    @Column(name = "mapY",updatable = false,nullable = true)
    Double mapY;

    @Column(name = "imgSrc",updatable = false,nullable = true, columnDefinition = "TEXT")
    String imgSrc;

    @Column(name = "operationTime",updatable = false,nullable = true , length = 1024)
    String operationTime;

    @Column(name = "breakTime",updatable = false,nullable = true , length = 1024)
    String breakTime;

    public NearbyStore() {
    }


}
