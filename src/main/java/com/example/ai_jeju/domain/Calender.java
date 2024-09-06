package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="calenders")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long calnenderId;

    @Column(name = "year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "day")
    private String day;

    @Column(name = "description")
    private String desc;


}
