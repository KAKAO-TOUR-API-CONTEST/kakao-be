package com.example.ai_jeju.controller;

import com.example.ai_jeju.dto.ChildRequest;
import com.example.ai_jeju.dto.ModifyMyPageRequest;
import com.example.ai_jeju.dto.MyPageResponse;
import com.example.ai_jeju.exception.UserNotFoundException;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.MyPageService;
import com.example.ai_jeju.service.S3Service;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyPageController {

    private final TokenProvider tokenProvider;
    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    public MyPageController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    @GetMapping("/mypage")
    public ResponseDto myPage(@RequestHeader("Authorization") String token){
        // Bearer 토큰 형식에서 "Bearer " 부분 제거
        String accessToken = token.replace("Bearer ", "");
        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);
            return ResponseUtil.SUCCESS("마이페이지 조회에 성공하였습니다.", userService.getMyPage(userId));
        } else {
            return ResponseUtil.ERROR("마이페이지 조회 중 문제가 발생하였습니다.", null);
        }
    }

    @PostMapping("/mypage/child")
    public ResponseDto myPageAddChild(@RequestHeader("Authorization") String token, @RequestBody ChildRequest childRequest){
        // Bearer 토큰 형식에서 "Bearer " 부분 제거
        String accessToken = token.replace("Bearer ", "");
        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);
            try{
                userService.registerChild(userId,childRequest);
                return ResponseUtil.SUCCESS("아이 추가에 성공하였습니다.", null);
            }catch (Exception e){
                return ResponseUtil.ERROR(e.getMessage(), null);
            }
        } else {
            return ResponseUtil.ERROR("토큰 유효성 문제가 발생하였습니다.", null);
        }
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
    public ResponseDto updateMyPage (@RequestHeader("Authorization") String token, @RequestBody ModifyMyPageRequest modifyMyPageRequest) {

        String accessToken = token.replace("Bearer ", "");

        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);
            try {
                myPageService.updateUser(userId, modifyMyPageRequest);
                return  ResponseUtil.SUCCESS("마이페이지 수정에 성공하였습니다.", null);
            } catch (UserNotFoundException e) {
                return ResponseUtil.FAILURE(e.getMessage(), null);
            } catch (Exception e) {
                return ResponseUtil.FAILURE(e.getMessage(), null);
            }
        }else{
            return ResponseUtil.FAILURE("고객 정보를 찾지 못하였습니다.", null);
        }
    }


}
