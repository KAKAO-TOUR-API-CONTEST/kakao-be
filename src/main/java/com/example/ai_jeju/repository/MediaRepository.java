package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

   // public Optional<Media> findMediaByAlbum_AlbumId(Long albumId);


}
