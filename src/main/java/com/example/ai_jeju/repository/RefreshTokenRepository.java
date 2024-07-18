package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>  {


}
