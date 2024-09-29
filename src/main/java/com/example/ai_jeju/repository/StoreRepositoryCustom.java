package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.dto.FilterDto;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface StoreRepositoryCustom {
    Map<String, Object> findByFilterDto(FilterDto filterDto, int randomSeed, int page);
    List<Store> findTasteOption(int category,int randomSeed);

    }
