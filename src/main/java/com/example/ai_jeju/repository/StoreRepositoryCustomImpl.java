package com.example.ai_jeju.repository;
import com.example.ai_jeju.domain.QStore;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.FilterDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

@Repository
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Override
    public List<Store> findByFilterDto(FilterDto filterDto) {

//        Boolean parking = filterDto.getParking().orElse(null);
//        Boolean strollerVar = filterDto.getStrollerVal().orElse(null);
//        String noKidsZone = filterDto.getNoKidsZone().orElse(null);
//        Boolean playground = filterDto.getPlayground().orElse(null);
//        Boolean babySpareChair = filterDto.getBabySpareChair().orElse(null);
//        Boolean rcmd = filterDto.getRcmd().orElse(null);

        QStore qStore = QStore.store;

        BooleanBuilder builder = new BooleanBuilder();
        // parking 조건 추가
        if (filterDto.getParking() != null && filterDto.getParking().isPresent()) {
            Boolean parkingValue = filterDto.getParking().orElse(null);
            if (parkingValue != null) {
                BooleanExpression parkingExpression = qStore.parking.eq(parkingValue);
                builder.and(parkingExpression);
            }
        }

        // playground 조건 추가
        if (filterDto.getPlayground() != null && filterDto.getPlayground().isPresent()) {
            Boolean playgroundValue = filterDto.getPlayground().orElse(null);
            if (playgroundValue != null) {
                BooleanExpression playgroundExpression = qStore.playground.eq(playgroundValue);
                builder.and(playgroundExpression);
            }
        }

        // babySpareChair 조건 추가
        if (filterDto.getBabySpareChair() != null && filterDto.getBabySpareChair().isPresent()) {
            Boolean babySpareChairValue = filterDto.getBabySpareChair().orElse(null);
            if (babySpareChairValue != null) {
                BooleanExpression babySpareChairExpression = qStore.babySpareChair.eq(babySpareChairValue);
                builder.and(babySpareChairExpression);
            }
        }

        // strollerVar 조건 추가
        if (filterDto.getStrollerVal() != null && filterDto.getStrollerVal().isPresent()) {
            Boolean strollerValValue = filterDto.getStrollerVal().orElse(null);
            if (strollerValValue != null) {
                BooleanExpression strollerVarExpression = qStore.strollerVal.eq(strollerValValue);
                builder.and(strollerVarExpression);
            }
        }

        //noKidsZone 조건
        if (filterDto.getKidsZone() != null && filterDto.getKidsZone().isPresent()) {
            Boolean kidsZoneValue = filterDto.getKidsZone().orElse(null);
            //아니면 무조건 true 겠지
            if (kidsZoneValue != null) {
                //조건 반전시키기 eq 아니고 ne
                BooleanExpression kidsZoneExpression = qStore.noKidsZone.ne("No키즈존");
                builder.and(kidsZoneExpression);
            }
        }

        // rcmd 조건 추가
        if (filterDto.getRcmd() != null && filterDto.getRcmd().isPresent()) {
            Boolean rcmdValue = filterDto.getRcmd().orElse(null);
            if (rcmdValue != null) {
                BooleanExpression rcmdExpression = qStore.rcmd.eq(rcmdValue);
                builder.and(rcmdExpression);
            }
        }

        // rcmd 조건 추가
        if (filterDto.getCategoryId() != null && filterDto.getCategoryId().isPresent()) {
            Integer categoryValue = filterDto.getCategoryId().orElse(null);
            if (categoryValue != null) {
                BooleanExpression categoryExpression = qStore.categoryId.eq(categoryValue);
                builder.and(categoryExpression);
            }
        }


        // rcmd 조건 추가
        if (filterDto.getKeyword() != null && filterDto.getKeyword().isPresent()) {
            String keywordValue = filterDto.getKeyword().orElse(null);
            if (keywordValue != null) {
                // name 필드에 keywordValue를 포함하는 조건 추가
                BooleanExpression nameContainsKeyword = qStore.name.contains(keywordValue);
                builder.and(nameContainsKeyword);
            }
        }

        // 동적 쿼리 실행
        return queryFactory.selectFrom(qStore)
                .where(builder)
                .fetch();
    }
}
