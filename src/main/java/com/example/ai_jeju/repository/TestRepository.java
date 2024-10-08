package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.Emergency;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.Test;
import com.example.ai_jeju.domain.Test2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test2, Long> {



}
