package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="bookmarks")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bmk_id")
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "store_id")
    private Long storeId;
}