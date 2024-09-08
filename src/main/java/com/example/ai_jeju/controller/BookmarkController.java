package com.example.ai_jeju.controller;

import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.BookmarkService;
import com.example.ai_jeju.service.MainVIewService;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {


    @Autowired
    private BookmarkService bookmarkService;

    private final TokenProvider tokenProvider;

    public BookmarkController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("")
    public ResponseDto getUserById(@RequestHeader("Authorization") String token, @RequestParam Long storeId) {

        String accessToken = token.replace("Bearer ", "");

        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);
            bookmarkService.addBookmark(userId, storeId);
            return ResponseUtil.SUCCESS("관심목록에 성공적으로 등록하였습니다.",null);

        }
        else {
            return ResponseUtil.ERROR("북마크 등록 중 문제가 발생하였습니다.", null);
        }

    }

    @DeleteMapping("")
    public ResponseDto deleteBookmark(@RequestHeader("Authorization") String token, @RequestParam Long storeId) {

        String accessToken = token.replace("Bearer ", "");

        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);
            bookmarkService.deleteBookmark(userId,storeId);
            return ResponseUtil.SUCCESS("관심목록을 성공적으로 삭제하였습니다.",null);
        }
        else {
            return ResponseUtil.ERROR("관심목록 삭제중 발생하였습니다.", null);
        }

    }

}
