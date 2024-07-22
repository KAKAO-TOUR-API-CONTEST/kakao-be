package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.User;
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

    public void updateSnsProfile(Long id, String snsprofile) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setSnsprofile(snsprofile);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(Long id, String profileimg) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileimg(profileimg); // S3에 업로드된 이미지의 URL을 프로필 이미지로 설정
        userRepository.save(user);
    }

    //프로필 이미지 반환
    public String getProfileUrl(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return user.getProfileimg(); // 프로필 이미지 URL 반환
    }

    public void deleteSnsProfile(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSnsprofile(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("not found");
        }
    }

    public void deleteProfileImage(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileimg(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("not found");
        }
    }
}
