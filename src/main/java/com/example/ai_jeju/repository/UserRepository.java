package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.User;
import jakarta.transaction.Transactional;
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

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.valid = :valid, u.nickname = :nickname, u.profileImg = :profileImg," +
            "u.provider = :provider , u.rgtDate = :rgtDate, u.phoneNum = :phoneNum," +
            "u.ifRcmd = :ifRcmd, u.selectedCk1 = :selectedCk1, u.selectedCk2 = :selectedCk2 WHERE u.id = :id")
    void updateUser(@Param("id") Long userId, @Param("valid") Boolean valid, @Param("nickname") String nickname,
                    @Param("profileImg") String profileImg, @Param("provider") String provider,
                    @Param("rgtDate") String rgtDate, @Param("phoneNum") String phoneNum,
                    @Param("ifRcmd") Boolean ifRcmd,
                    @Param("selectedCk1") Boolean selectedCk1, @Param("selectedCk2") Boolean selectedCk2);



}