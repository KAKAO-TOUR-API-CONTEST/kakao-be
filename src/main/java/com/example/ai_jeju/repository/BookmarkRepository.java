package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {


// userId와 storeId를 기준으로 북마크가 존재하는지 여부를 확인하는 메서드
boolean existsByUserAndStoreId(User user, Long storeId);
List<Bookmark> findByStoreId(Long storeId);
List<Bookmark> findByUser(User user);
void deleteByUserAndStoreId(User user, Long storeId);
void deleteByUser(User user);




}
