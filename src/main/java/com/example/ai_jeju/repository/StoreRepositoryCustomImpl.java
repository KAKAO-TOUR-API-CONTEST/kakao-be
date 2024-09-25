package com.example.ai_jeju.repository;
import com.example.ai_jeju.domain.QBookmark;
import com.example.ai_jeju.domain.QStore;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.FilterDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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
    public List<Store> findByFilterDto(FilterDto filterDto, int randomSeed, int page) {

        QStore qStore = QStore.store;

        int pageSize = 50; // 한 페이지에 보여줄 개수

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

        // rcmdType 조건 추가
        if (filterDto.getRcmdType() != null && filterDto.getRcmdType().isPresent()) {
            Integer rcmdTypeValue = filterDto.getRcmdType().orElse(null);
            if (rcmdTypeValue != null) {
                BooleanExpression rcmdExpression = qStore.rcmdType.eq(rcmdTypeValue);
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

        // 랜덤 시드를 적용한 랜덤 정렬 추가
        NumberExpression<Double> randomExpression = Expressions.numberTemplate(Double.class, "RAND({0})", randomSeed);

        // bookmarks순으로 정렬하기

            Boolean popularityValue = filterDto.getPopularity().orElse(null);
            if (popularityValue!=null&&popularityValue) {

                return queryFactory.selectFrom(qStore)
                        .where(builder)
                        .orderBy(qStore.noBmk.desc(), randomExpression.asc())
                        .limit(pageSize) // 50개씩 제한
                        .offset((page - 1) * pageSize) // 페이지 번호에 따라 오프셋 적용// bookmarks를 내림차순으로 정렬
                        .fetch();
            }

        // 동적 쿼리 실행 (popularity가 null이거나 false인 경우)
        return queryFactory.selectFrom(qStore)
                .where(builder)
                .orderBy(randomExpression.asc())
                .limit(pageSize) // 50개씩 제한
                .offset((page - 1) * pageSize) // 페이지 번호에 따라 오프셋 적용// 랜덤 정렬 추가
                .fetch();

    }
}
