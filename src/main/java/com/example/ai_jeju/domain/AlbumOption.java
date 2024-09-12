package com.example.ai_jeju.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name ="album_item_options")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@AllArgsConstructor
@Builder
public class AlbumOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="album_item_option_id",updatable = false)
    private Long albumItemId;

    @Column(name="op1")
    private boolean op1;

    @Column(name="op2")
    private boolean op2;

    @Column(name="op3")
    private boolean op3;

    @Column(name="op4")
    private boolean op4;

    @Column(name="op5")
    private boolean op5;

    @Column(name="op6")
    private boolean op6;

    @Column(name="op7")
    private boolean op7;

    @Column(name="op8")
    private boolean op8;

    @Column(name="op9")
    private boolean op9;

    @Column(name="op10")
    private boolean op10;

    @Column(name="op11")
    private boolean op11;

    @Column(name="op12")
    private boolean op12;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
