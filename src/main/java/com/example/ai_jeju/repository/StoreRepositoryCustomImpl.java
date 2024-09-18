package com.example.ai_jeju.repository;
import com.example.ai_jeju.domain.QStore;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.FilterDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<Store> findByFilterDto(FilterDto filterDto) {

        Boolean parking = filterDto.getParking().orElse(null);
        Boolean strollerVar = filterDto.getStrollerVal().orElse(null);
        String noKidsZone = filterDto.getNoKidsZone().orElse("NO키즈존");
        Boolean playground = filterDto.getPlayground().orElse(null);
        Boolean babySpareChair = filterDto.getBabySpareChair().orElse(null);
        Boolean rcmd = filterDto.getRcmd().orElse(null);


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
        // 동적 쿼리 실행
        return queryFactory.selectFrom(qStore)
                .where(builder)
                .fetch();
    }
}
