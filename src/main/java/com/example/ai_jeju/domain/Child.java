package com.example.ai_jeju.domain;

import com.example.ai_jeju.repository.AlbumRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

// test
@Table(name="child")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Setter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id", updatable = false, unique = true)
    private Long childId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="schedule_id")
    private Long scheduleId;

    @Column
    private String birthDate;

    @Column
    private String childName;

    @Column
    private Boolean gender;

    @Column
    private String realtion;

    @Column
    private String childProfile;

    //와하하 영속성문제 해결 하하하하
    @PostPersist
    public void postPersist() {
        // 데이터베이스에서 자동 생성된 ID를 다른 필드에 설정
        this.scheduleId = this.childId;

    }

}