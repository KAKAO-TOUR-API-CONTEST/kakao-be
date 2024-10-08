package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;


import java.util.Collection;
import java.util.List;


@Table(name="users")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Setter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Column(name="email", nullable = false, unique = true)
    private String email;
    //실명
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "nickname",nullable = false,unique = true)
    private String nickname;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name="profileImg")
    private String profileImg;

    @Column(name="rgtDate", nullable = false)
    private String rgtDate;

    @Column(name="phoneNum", nullable = false)
    private String phoneNum;

    @Column(name ="ifRcmd", nullable = false)
    private boolean ifRcmd;

    @Column(name ="valid", nullable = true)
    private boolean valid;

    @Column(name ="selectedCk1", nullable = false)
    private boolean selectedCk1;
    @Column(name ="selectedCk2", nullable = false)
    private boolean selectedCk2;
}
