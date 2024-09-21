package com.example.ai_jeju.domain;

import com.example.ai_jeju.dto.AlbumOptionDto;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.*;
import lombok.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Table(name ="album_options")
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

    @Column(name="optionalFriend")
    private boolean optionalFriend;

    @Column(name="optionalPet")
    private boolean optionalPet;

    @Column(name="optionalFamily")
    private boolean optionalFamily;


    @Column(name="optionalMorning")
    private boolean optionalMorning;

    @Column(name="optionalAm")
    private boolean optionalAfterNoon;

    @Column(name="optionalPm")
    private boolean optionalNight;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    public AlbumOptionDto toDto() {
        AlbumOptionDto.AlbumOptionDtoBuilder dtoBuilder = AlbumOptionDto.builder();

        if (this.optionalPet) {
            dtoBuilder.optionalPet(JsonNullable.of(true));
        }
        if (this.optionalFriend) {
            dtoBuilder.optionalFriend(JsonNullable.of(true));
        }
        if (this.optionalFamily) {
            dtoBuilder.optionalFamily(JsonNullable.of(true));
        }
        if (this.optionalMorning) {
            dtoBuilder.optionalMorning(JsonNullable.of(true));
        }
        if (this.optionalAfterNoon) {
            dtoBuilder.optionalAm(JsonNullable.of(true));
        }
        if (this.optionalNight) {
            dtoBuilder.optionalPm(JsonNullable.of(true));
        }
        if (this.optionalDining) {
            dtoBuilder.optionalDining(JsonNullable.of(true));
        }
        if (this.optionalSnack) {
            dtoBuilder.optionalSnack(JsonNullable.of(true));
        }
        if (this.optionalPlay) {
            dtoBuilder.optionalPlay(JsonNullable.of(true));
        }
        if (this.optionalStudy) {
            dtoBuilder.optionalStudy(JsonNullable.of(true));
        }
        if (this.optionalExperience) {
            dtoBuilder.optionalExperience(JsonNullable.of(true));
        }
        if (this.optionalWalk) {
            dtoBuilder.optionalWalk(JsonNullable.of(true));
        }

        return dtoBuilder.build();
    }


}
