package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.NearbyStore;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.repository.NearbyStoreRepository;
import com.example.ai_jeju.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.ai_jeju.domain.QStore.store;

@Service
public class StoreService {

    @Autowired
    private NearbyStoreRepository nearbyStoresRepository;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private EntityManager entityManager;


    public void updateNoBmk(Long storeId, int noBmk) {

            // JPQL을 사용하여 noBmk 값을 직접 수정
            System.out.println("북마크 수"+noBmk);
            entityManager.createQuery("UPDATE Store s SET s.noBmk = :noBmk WHERE s.storeId = :storeId")
                    .setParameter("noBmk", noBmk)
                    .setParameter("storeId", storeId)
                    .executeUpdate();



    }



    public List<Store> getNearbyStores(Long storeId) {
        // Step 1: Get nearby store IDs
        List<NearbyStore> nearbyStores = nearbyStoresRepository.findByStoreId(storeId);
        List<Long> nearbyStoreIds = nearbyStores.stream()
                .map(NearbyStore::getNearbyStoreId)
                .collect(Collectors.toList());

        // Step 2: Get store details using nearbyStoreIds
        return storeRepository.findByStoreIdIn(nearbyStoreIds);
    }



}
