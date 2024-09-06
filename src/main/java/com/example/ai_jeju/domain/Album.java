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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", nullable = false)
    private Long albumId;

    @OneToOne
    @JoinColumn(name = "child_id")
    private Child child;


}
