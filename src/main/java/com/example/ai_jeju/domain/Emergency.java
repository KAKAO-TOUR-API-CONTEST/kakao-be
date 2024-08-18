package com.example.ai_jeju.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Table(name="emergency")
@Entity
@Getter
@Setter
public class Emergency {

    @Id
    private Long emergencyid;
    private String pharname;
    private String address;
    private String tel;
    private String imgsrc;
    private String operationtime;
    private String category;

}
