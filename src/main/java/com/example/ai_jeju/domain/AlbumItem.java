package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name="album_items")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class AlbumItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_item_id", updatable = false, unique = true)
    private Long albumItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    private Album album;

    @Column
    private String imgSrc;


}