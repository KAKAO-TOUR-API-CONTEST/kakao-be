package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.FilterDto;
import com.example.ai_jeju.dto.MainListResponse;
import com.example.ai_jeju.dto.DetailListResponse;
import com.example.ai_jeju.dto.MainResponse;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.repository.StoreRepositoryCustomImpl;
import com.example.ai_jeju.repository.UserRepository;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainVIewService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepositoryCustomImpl storeRepositoryCustom;

    public static String printCategory(int category) {
        switch (category) {
            case 1:
                return ("음식점");

            case 2:
                return ("호텔");

            case 3:
                return ("레저");

            default:
                return ("기타");

        }}

    public DetailListResponse getDetailList(Long userId,Long storeId){
        Optional<User> user = userRepository.findById(userId);
        Optional<Store> store = storeRepository.findById(storeId);

        List<Bookmark> bmks = bookmarkRepository.findByStoreId(storeId);
        if(store.isPresent()){
            Store innerStore = store.get();

            DetailListResponse detailListResponse = DetailListResponse.builder()
                    .storeId(innerStore.getStoreId())
                    .item(printCategory(innerStore.getCategoryId()))
                    .name(innerStore.getName())
                    .imgSrc(innerStore.getImgSrc())
                    .address(innerStore.getAddress())
                    .mapX(innerStore.getMapX())
                    .mapY(innerStore.getMapY())

                    .parking(innerStore.getParking())
                    .pet(innerStore.getPet())
                    .stroller(innerStore.getStroller())
                    .playground(innerStore.getPlayground())

                    .babySpareChair(innerStore.getBabySpareChair())
                    .playground(innerStore.getPlayground())

                    .strollerVal(innerStore.getStrollerVal())

                    .noKidsZone(innerStore.getNoKidsZone())
                    .categoryId(innerStore.getCategoryId())
                    .operationTime(innerStore.getOperationTime())
                    .checkin(innerStore.getCheckin())
                    .checkout(innerStore.getCheckout())
                    .tel(innerStore.getTel())
                    .noBmk(bmks.size())
                    .bmkStatus(bookmarkRepository.existsByUserAndStoreId(user.get(),storeId))
                    .build();

            return detailListResponse;
        }else return null;

    }

    public DetailListResponse getDetailList(Long storeId){
        //Optional<User> user = userRepository.findById(userId);
        Optional<Store> store = storeRepository.findById(storeId);
        //List<Bookmark> bmks = bookmarkRepository.findByStoreId(storeId);
        if(store.isPresent()){
            Store innerStore = store.get();
            DetailListResponse detailListResponse = DetailListResponse.builder()
                    .storeId(innerStore.getStoreId())
                    .item(printCategory(innerStore.getCategoryId()))
                    .name(innerStore.getName())
                    .imgSrc(innerStore.getImgSrc())
                    .address(innerStore.getAddress())
                    .mapX(innerStore.getMapX())
                    .mapY(innerStore.getMapY())
                    //
                    .pet(innerStore.getPet())
                    .parking(innerStore.getParking())
                    .stroller(innerStore.getStroller())
                    //
                    .strollerVal(innerStore.getStrollerVal())
                    .babySpareChair(innerStore.getBabySpareChair())
                    .playground(innerStore.getPlayground())
                    .noKidsZone(innerStore.getNoKidsZone())
                    .categoryId(innerStore.getCategoryId())
                    .operationTime(innerStore.getOperationTime())
                    //
                    .checkin(innerStore.getCheckin())
                    .checkout(innerStore.getCheckout())
                    //
                    .tel(innerStore.getTel())
                    .noBmk(innerStore.getNoBmk())
                    .bmkStatus(false)
                    .build();

            return detailListResponse;
        }else return null;

    }


    public List<MainListResponse> getMainList(Long userId){

        List<Store> storeList= storeRepository.findAllOrderByRandomNative();
        Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses= new ArrayList<>();

        for(Store store : storeList){

            List<Bookmark> bmks = bookmarkRepository.findByStoreId(store.getStoreId());

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bmks.size())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    public List<MainListResponse> getMainList(){

        List<Store> storeList= storeRepository.findAllOrderByRandomNative();

        System.out.println("Store List Size: " + storeList.size());  // 로그 확인
        //Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses= new ArrayList<>();

        for (Store store : storeList) {
            if (store == null) {
                System.out.println("Store is null");
            } else {
                System.out.println("Store ID: " + store.getStoreId());
            }
        }

        for(Store store : storeList){

            List<Bookmark> bmks = bookmarkRepository.findByStoreId(store.getStoreId());

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bmks.size())
                    .bmkSatus(false)
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    public List<MainListResponse> getListByCategory(Long userId, int categoryId){

        List<Store> stores = storeRepository.findByCategoryId(categoryId);
        Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();

            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    public List<MainListResponse> getListByCategory(int categoryId){

        List<Store> stores = storeRepository.findByCategoryId(categoryId);

        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());

            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(false)
                    .build();

            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    // 회원 - 검색어로 조회하기.
    public List<MainListResponse> getListBySearch(Long userId, String keyword){
        List<Store> stores = storeRepository.findBySearch(keyword);
        Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    // 비회원 - 검색어로 조회하기.
    public List<MainListResponse> getListBySearch(String keyword){
        List<Store> stores = storeRepository.findBySearch(keyword);

        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(false)
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;
    }

    // 비회원 - 검색어로 조회결과 카테고리 정렬하기
    public List<MainListResponse> searchByCategory(String keyword, int categoryId){

        List<Store> stores = storeRepository.findByKeywordAndCategoryId(keyword,categoryId);
        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(false)
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;

    }
    // 회원 - 검색어로 조회결과 카테고리 정렬하기
    public List<MainListResponse> searchByCategory(Long userId, String keyword, int categoryId){

        List<Store> stores = storeRepository.findByKeywordAndCategoryId(keyword,categoryId);
        Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses = new ArrayList<>();
        for(Store store : stores){
            List<Bookmark> bookmarks = bookmarkRepository.findByStoreId(store.getStoreId());
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(bookmarks.size())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();
            mainListResponses.add(mainListResponse);
        }
        return mainListResponses;

    }

    // BooleanExpression -> null이면 쿼리에 문제 생기지 x
    public MainResponse getMain(FilterDto filterDto, Long userId, int randomSeed, int page){

        Map<String, Object> result =storeRepositoryCustom.findByFilterDto(filterDto,randomSeed, page);
        Optional<User> user = userRepository.findById(userId);
        List<MainListResponse> mainListResponses = new ArrayList<>();
        List<Store> stores = (List<Store>) result.get("stores");

        for(Store store : stores){
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(store.getNoBmk())
                    .bmkSatus(bookmarkRepository.existsByUserAndStoreId(user.get(),store.getStoreId()))
                    .build();
            mainListResponses.add(mainListResponse);
        }
        MainResponse mainResponse = new MainResponse(mainListResponses, (Long) result.get("totalPages"));
        return mainResponse;
    }

    public MainResponse getMain(FilterDto filterDto, int randomSeed, int page){

        Map<String, Object> result =storeRepositoryCustom.findByFilterDto(filterDto, randomSeed,page);

        List<MainListResponse> mainListResponses = new ArrayList<>();

        List<Store> stores = (List<Store>) result.get("stores");

        for(Store store : stores){
            MainListResponse mainListResponse = MainListResponse.builder()
                    .storeId(store.getStoreId())
                    .name(store.getName())
                    .imgSrc(store.getImgSrc())
                    .address(store.getAddress())
                    .noKidsZone(store.getNoKidsZone())
                    .noBmk(store.getNoBmk())
                    .bmkSatus(false)
                    .build();
            mainListResponses.add(mainListResponse);
        }

        MainResponse mainResponse = new MainResponse(mainListResponses,(Long) result.get("totalPages"));
        return mainResponse;

    }




}
