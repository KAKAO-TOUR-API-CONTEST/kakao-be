package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Restaurant;
import com.example.ai_jeju.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StayRepository extends JpaRepository<Stay,Long> {

    Optional<Stay> findByStayId(int stayId);

}
