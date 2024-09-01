package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.RefreshToken;
import com.example.ai_jeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    //findByUserId(userId)
    Optional<RefreshToken> findByUserId(Long userId);
   // Optional<RefreshToken> findByEmail(String email);
}
