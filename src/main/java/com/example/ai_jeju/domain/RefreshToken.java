package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="refreshToken")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Column
    String refresh_token;

    @Column
    private Long userId;


    //생성자
    public RefreshToken(Long userId, String refreshToken){
        this.userId = userId;
        this.refresh_token = refreshToken;
    }

    //update 메서드
    public RefreshToken update(String newRefreshToken){
        this.refresh_token = newRefreshToken;
        return  this;
    }


}
