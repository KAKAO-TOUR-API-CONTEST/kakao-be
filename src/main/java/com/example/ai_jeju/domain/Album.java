package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="album")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Album {

    @Id
    @OneToOne
    @JoinColumn(name = "album_id") // FK를 설정
    private Child child;

}
