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

    @Column(name="optionalPet")
    private boolean optionalPet;

    @Column(name="optionalFriend")
    private boolean optionalFriend;

    @Column(name="optionalFamily")
    private boolean optionalFamily;

    @Column(name="optionalMorning")
    private boolean optionalMorning;

    @Column(name="optionalAm")
    private boolean optionalAm;

    @Column(name="optionalPm")
    private boolean optionalPm;

    @Column(name="optionalDining")
    private boolean optionalDining;

    @Column(name="optionalSnack")
    private boolean optionalSnack;

    @Column(name="optionalPlay")
    private boolean optionalPlay;

    @Column(name="optionalStudy")
    private boolean optionalStudy;

    @Column(name="optionalExperience")
    private boolean optionalExperience;

    @Column(name="optionalWalk")
    private boolean optionalWalk;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
