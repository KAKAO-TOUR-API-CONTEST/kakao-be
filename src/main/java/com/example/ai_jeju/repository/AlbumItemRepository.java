package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.AlbumItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumItemRepository extends JpaRepository<AlbumItem,Long> {
}
