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


    public void updateNoBmk(Long storeId, int noBmk) {

            // JPQL을 사용하여 noBmk 값을 직접 수정
            System.out.println("북마크 수"+noBmk);
            entityManager.createQuery("UPDATE Store s SET s.noBmk = :noBmk WHERE s.storeId = :storeId")
                    .setParameter("noBmk", noBmk)
                    .setParameter("storeId", storeId)
                    .executeUpdate();



    }

}
