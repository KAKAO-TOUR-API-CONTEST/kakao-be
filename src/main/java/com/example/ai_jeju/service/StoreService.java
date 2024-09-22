package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.ai_jeju.domain.QStore.store;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void updateNoBmk(Long storeId, int noBmk) {

        if (store != null) {
            // JPQL을 사용하여 noBmk 값을 직접 수정
            entityManager.createQuery("UPDATE Store s SET s.noBmk = :noBmk WHERE s.storeId = :storeId")
                    .setParameter("noBmk", noBmk)
                    .executeUpdate();
        } else {
            // Store 객체가 존재하지 않을 때의 처리
            System.out.println("Store not found with id: " + storeId);
        }

    }

}
