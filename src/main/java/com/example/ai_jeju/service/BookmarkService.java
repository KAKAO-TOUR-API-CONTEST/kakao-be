package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.BookMarkItem;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.repository.UserRepository;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    @Transactional
    public void addBookmark(Long userId, Long storeId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
                Bookmark bookmark = Bookmark.builder()
                        .user(user.get())
                        .storeId(storeId)
                        .build();


                bookmarkRepository.save(bookmark);
                List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(storeId);
                System.out.println("북마크 수 "+bookmarks.size());
                storeRepository.updateBookmarks(storeId,bookmarks.size());
            }

        }


    @Transactional
    public void deleteBookmark(Long userId, Long storeId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            if (bookmarkRepository.existsByUserAndStoreId(user.get(), storeId)) {
                bookmarkRepository.deleteByUserAndStoreId(user.get(), storeId);
            }
        }
    }

    public List<BookMarkItem> getBookmark(Long userId) {
        List<BookMarkItem> bookMarkItems = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Bookmark> bookmarks = bookmarkRepository.findByUser(user.get());
            for (Bookmark bookmark : bookmarks) {
                //1. boookmark의 storeId 기반으로 스토어들을 찾고
                Optional<Store> store = storeRepository.findByStoreId(bookmark.getStoreId());
                //2. 그 storeId를 몇명이나 북마크로 등록했는지를 확인한다.
                List<Bookmark> bmks = bookmarkRepository.findByStoreId(store.get().getStoreId());
                if (store.isPresent()) {
                    BookMarkItem bookMarkItem = BookMarkItem.builder()
                            .storeId(bookmark.getStoreId())
                            .storeName(store.get().getName())
                            .imgsrc(store.get().getImgSrc())
                            .noKidszone(store.get().getNoKidsZone())
                            .numOfBmk(bmks.size())
                            .build();
                    bookMarkItems.add(bookMarkItem);
                } else {
                    return null;
                }
            }
        }
        return bookMarkItems;
    }
        public List<BookMarkItem> getBookmarkByCategoryId (Long userId, int categoryId){
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                List<Bookmark> bookmarks = bookmarkRepository.findByUser(user.get());
                List<BookMarkItem> bookMarkItems = new ArrayList<>();
                for (Bookmark bookmark : bookmarks) {
                    Optional<Store> store = storeRepository.findByStoreId(bookmark.getStoreId());
                    List<Bookmark> bmks = bookmarkRepository.findByStoreId(store.get().getStoreId());
                    //그리고 카테고리 아이디가 내가 제시한 카테고리 아이디랑 같다면,
                    if(store.isPresent()&&categoryId==store.get().getCategoryId() ){
                        BookMarkItem bookMarkItem = BookMarkItem.builder()
                                .storeId(bookmark.getStoreId())
                                .storeName(store.get().getName())
                                .noKidszone(store.get().getNoKidsZone())
                                .numOfBmk(bmks.size())
                                .build();
                        bookMarkItems.add(bookMarkItem);
                        return bookMarkItems;
                    }
                }
            }
            return null;
        }


}
