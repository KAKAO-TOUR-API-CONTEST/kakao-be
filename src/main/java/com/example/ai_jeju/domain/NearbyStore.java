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


    public NearbyStore() {
    }


}
