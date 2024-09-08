package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="album")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", updatable = false)
    private Long albumId;

    @Column(name = "album_title")
    private String albumTitle;

    @Column(name = "album_desc")
    private String albumDesc;

    @Column(name = "rep_imgsrc")
    private String repImgSrc;

    @ManyToOne
    @JoinColumn(name="child_id")
    private Child child;

    @Column
    private String rgtDate;

}
