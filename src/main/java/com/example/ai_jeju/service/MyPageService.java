package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.ModifyMyPageRequest;
import com.example.ai_jeju.exception.UserNotFoundException;
import com.example.ai_jeju.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyPageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3Service s3Service;

    //마이페이지 회원 한명 id로 찾기
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateNickname(Long id, String nickname) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(Long id, String profileimg) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileImg(profileimg); // S3에 업로드된 이미지의 URL을 프로필 이미지로 설정
        userRepository.save(user);
    }

    //프로필 이미지 반환
    public String getProfileUrl(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return user.getProfileImg(); // 프로필 이미지 URL 반환
    }

    public void deleteProfileImage(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileImg(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("not found");
        }
    }

    public void updateUser(Long userId, ModifyMyPageRequest modifyMyPageRequest) {
        //수정당할 애 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // 닉네임 업데이트
        modifyMyPageRequest.getNickname().ifPresent(nickname -> {
            user.setNickname(nickname);
        });

        // 전화번호 업데이트
        modifyMyPageRequest.getPhoneNum().ifPresent(phoneNum -> {
            user.setPhoneNum(phoneNum);
        });
        // 변경된 사용자 정보를 저장
        userRepository.save(user);
    }
}
