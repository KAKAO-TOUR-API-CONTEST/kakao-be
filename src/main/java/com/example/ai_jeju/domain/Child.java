package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Table(name="child")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id", updatable = false, unique = true)
    private Long childId;

    @Column
    private Long userId;

    @Column
    private String birthDate;

    @Column
    private String childName;

    @Column
    private Boolean gender;

    @Column
    private String realtion;

    @Column
    private long childProfile;
}
