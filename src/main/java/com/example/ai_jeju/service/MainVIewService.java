package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Bookmark;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.StoreResponse;
import com.example.ai_jeju.repository.BookmarkRepository;
import com.example.ai_jeju.repository.StoreRepository;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MainVIewService {


    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public ResponseDto getDetailList(Long storeId){

        Optional<Store> store = storeRepository.findById(storeId);

        List<Bookmark> bmks = bookmarkRepository.findByStoreId(storeId);

        if(store.isPresent()){
            Store innerStore = store.get();
            StoreResponse storeResponse = StoreResponse.builder()
                    .storeId(innerStore.getStoreId())
                    .name(innerStore.getName())
                    .imgSrc(innerStore.getImgSrc())
                    .address(innerStore.getAddress())
                    .mapX(innerStore.getMapX())
                    .mapY(innerStore.getMapY())
                    .stroller(innerStore.getStroller())
                    .strollerVal(innerStore.getStrollerVal())
                    .babySpareChair(innerStore.getBabySpareChair())
                    .playground(innerStore.getPlayground())
                    .noKidsZone(innerStore.getNoKidsZone())
                    .categoryId(innerStore.getCategoryId())
                    .operationTime(innerStore.getOperationTime())
                    .tel(innerStore.getTel())
                    .noBmk(bmks.size())
                    .build();

            return ResponseUtil.SUCCESS("detail List 조회 성공하셨습니다.",storeResponse);
        }
        else {
            return ResponseUtil.FAILURE("해당 storeId는 존재하지 않습니다.", null);

        }
    }

    public List<Store> getMainList(){
        return storeRepository.findAllOrderByRandomNative();
    }

    public List<Store>getListByCategory(int categoryId){
        return storeRepository.findByCategoryId(categoryId);
    }

    public List<Store>getListBySearch(String keyword){
        return storeRepository.findBySearch(keyword);
    }

    public List<Store>searchByCategory(String keyword, int categoryId){
        return storeRepository.findByKeywordAndCategoryId(keyword,categoryId);
    }



}
