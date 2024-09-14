package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="schedule_items")
@NoArgsConstructor(access= AccessLevel.PROTECTED) //기본생성자
@Getter
@Entity
@AllArgsConstructor // 모든 필드를 초기화하는 생성자
@Builder // 빌더 패턴
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_item_id", updatable = false, unique = true)
    private Long scheduleItemId;

    private String year;
    private String month;
    private String day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id", referencedColumnName = "schedule_id")
    Child child;

}
