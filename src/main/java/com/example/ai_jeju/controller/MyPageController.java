package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.MyPageResponse;
import com.example.ai_jeju.service.MyPageService;
import com.example.ai_jeju.service.S3Service;
import com.example.ai_jeju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    //마이페이지 회원 1명씩 조회

//    @GetMapping("/mypage")
//    public ResponseEntity<User> getUserById(@RequestParam Long userId) {
//        Optional<User> user = myPageService.getUserById(userId);
//        if (user.isPresent()) {
//            return ResponseEntity.ok(user.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/mypage")
    public MyPageResponse myPage(@RequestParam Long userId){
        return userService.getMyPage(userId);
    }

    @PutMapping("/mypage/nickname")
    public ResponseEntity<String> updateNickname(@RequestParam Long userId, @RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        myPageService.updateNickname(userId, nickname);
        return ResponseEntity.ok("Nickname changed");
    }


    //프로필 이미지 변경
    @PutMapping("/mypage/profileimg")
    public ResponseEntity<String> updateProfileImage(@RequestParam Long userId, @RequestBody Map<String, String> request) {
        String profileimg = request.get("profileimg");
        myPageService.updateProfile(userId, profileimg);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/mypage/profileimg")
    public ResponseEntity<String> deleteProfileImage(@RequestParam Long userId) {
        myPageService.deleteProfileImage(userId);
        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/mypage/presign")
    public ResponseEntity<String> createPresignedUrl(@RequestBody Map<String, String> request) {
        String filePath = request.get("filePath");
        String presignedUrl = s3Service.createPresignedUrl(filePath);
        return ResponseEntity.ok(presignedUrl);
    }


    @PutMapping("/mypage/update")
    public ResponseEntity<User> updateMyPage (@RequestBody User newUser) {

        Long userId = newUser.getId();

        try {
            User updatedUser = myPageService.updateUser(userId, newUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
