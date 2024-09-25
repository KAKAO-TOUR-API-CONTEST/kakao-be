package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.NearbyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearbyStoreRepository extends JpaRepository<NearbyStore, Long> {

    @Query("SELECT ns.nearbyStoreId FROM NearbyStores ns WHERE ns.storeId = :storeId")
    List<Long> findNearbyStoreIdsByStoreId(@Param("storeId") Long storeId);
}
