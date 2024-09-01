package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="photos")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Photo {

    @Id
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "img_src")
    private String imgSrc;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
