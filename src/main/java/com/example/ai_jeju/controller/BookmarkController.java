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

    //전체 랜덤 조회
    @GetMapping("")
    public ResponseDto getBookmark(@RequestHeader("Authorization") String token) {

        String accessToken = token.replace("Bearer ", "");

        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);

            return ResponseUtil.SUCCESS("관심목록을 성공적으로 조회하였습니다.", bookmarkService.getBookmark(userId));
        }
        else {
            return ResponseUtil.ERROR("관심목록 조회를 실패하였습니다.", null);
        }

    }
    //카테고리 별 조회
    @GetMapping("/category")
    public ResponseDto getBookmarkByCategoryId(@RequestHeader("Authorization") String token, @RequestParam int categoryId) {

        String accessToken = token.replace("Bearer ", "");

        if (tokenProvider.validToken(accessToken)) {
            Long userId = tokenProvider.getUserId(accessToken);

            return ResponseUtil.SUCCESS("카테고리별 관심목록 조회 성공하셨습니다.", bookmarkService.getBookmarkByCategoryId(userId,categoryId));
        }
        else {
            return ResponseUtil.ERROR("카테고리별 관심목록 조회에 실패하셨습니다.", null);
        }

    }

}
