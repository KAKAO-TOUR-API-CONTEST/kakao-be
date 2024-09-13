package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Album;
import com.example.ai_jeju.domain.AlbumOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumOptionRepository extends JpaRepository<AlbumOption, Long> {

    Optional<AlbumOption> findByAlbum(Album album);

}
