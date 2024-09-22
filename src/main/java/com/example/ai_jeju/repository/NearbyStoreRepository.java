package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.NearbyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearbyStoreRepository extends JpaRepository<NearbyStore, Long> {

    List<NearbyStore> findByStoreId(Long storeId);
}
