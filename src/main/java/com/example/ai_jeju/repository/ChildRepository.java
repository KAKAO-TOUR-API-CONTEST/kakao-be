package com.example.ai_jeju.repository;


import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("SELECT c FROM Child c WHERE c.user = :user")
    List<Child> findAllByUser(@Param("user") User user);

    Optional<Child> findByChildId(Long childId);

    void deleteByUser(User user);


}
