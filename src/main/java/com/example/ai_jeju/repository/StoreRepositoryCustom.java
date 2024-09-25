package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.FilterDto;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepositoryCustom {
    List<Store> findByFilterDto(FilterDto filterDto,int randomSeed, int page);
}
