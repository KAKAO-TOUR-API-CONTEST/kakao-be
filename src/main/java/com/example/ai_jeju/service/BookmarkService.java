package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public void addBookmark(Long userId,Long storeId){
        if(!bookmarkRepository.existsByUserIdAndStoreId(userId,storeId)){
            Bookmark bookmark = Bookmark.builder()
                    .userId(userId)
                    .storeId(storeId)
                    .build();
            bookmarkRepository.save(bookmark);
        }
    }

    @Transactional
    public void deleteBookmark(Long userId,Long storeId){
        if(bookmarkRepository.existsByUserIdAndStoreId(userId,storeId)){
            bookmarkRepository.deleteByUserIdAndStoreId(userId,storeId);
        }
    }

}
