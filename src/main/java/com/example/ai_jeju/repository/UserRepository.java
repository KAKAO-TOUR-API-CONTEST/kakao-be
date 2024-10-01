package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
    Optional<User>findUserByNickname(String nickname);


    @Modifying
    @Query("UPDATE User u set u.valid = :valid WHERE u.id = :id")
    void updateUserByValid(@Param("id") Long userId, @Param("valid") Boolean valid);


}