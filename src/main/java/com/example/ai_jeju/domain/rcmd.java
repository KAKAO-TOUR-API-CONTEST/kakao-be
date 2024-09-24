package com.example.ai_jeju.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="special_rcmd")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class rcmd {

    @Id
    @Column(name ="storeId", updatable = false, unique = true)
    Long rcmdId;



}
