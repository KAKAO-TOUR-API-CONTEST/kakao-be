package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findByCategory(String category);

}
