package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Album;
import com.example.ai_jeju.domain.AlbumItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumItemRepository extends JpaRepository<AlbumItem,Long> {

    //album 객체로 album item 찾기
    List<AlbumItem> findByAlbum(Album album);
}
