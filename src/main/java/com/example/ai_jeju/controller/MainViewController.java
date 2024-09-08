package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.MainVIewService;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainViewController {
    private final TokenProvider tokenProvider;
    @Autowired
    private MainVIewService mainViewService;
    @Autowired
    private UserService userService;

    public MainViewController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/detailList")
    public ResponseDto getUserById(@RequestHeader(value = "Authorization", required = false) String token,@RequestParam Long storeId) {
        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("상세 조회에 성공하였습니다.", mainViewService.getDetailList(userId,storeId));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {
                return ResponseUtil.ERROR("토큰 유효성 문제가 발생하였습니다.", null);
            }
        }else {
            // 토큰이 없는 경우 기본 데이터 반환
            try {
                return ResponseUtil.SUCCESS("비회원 상세 조회에 성공하였습니다.", mainViewService.getDetailList(null,storeId));
            } catch (Exception e) {
                return ResponseUtil.ERROR(e.getMessage(), null);
            }
        }
    }

    @GetMapping("/mainList")
    public ResponseDto getMainList(@RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null) {
            String accessToken = token.replace("Bearer ", "");

            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("메인리스트 조회에 성공하였습니다.", mainViewService.getMainList(userId));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {
                return ResponseUtil.ERROR("토큰 유효성 문제가 발생하였습니다.", null);
            }
        } else {
            // 토큰이 없는 경우 기본 데이터 반환
            try {
                return ResponseUtil.SUCCESS("비회원 메인 조회에 성공하였습니다.", mainViewService.getMainList(null));
            } catch (Exception e) {
                return ResponseUtil.ERROR(e.getMessage(), null);
            }
        }
    }

    @GetMapping("/mainList/filters")
    public List<Store> getListByCategory(@RequestParam int categoryId){
        return mainViewService.getListByCategory(categoryId);
    }

    @GetMapping("/searchList")
    public List<Store> getListBySearch(@RequestParam String keyword){
        return mainViewService.getListBySearch(keyword);
    }

    @GetMapping("/searchList/filters")
    public List<Store> searchByCategory(@RequestParam String keyword, @RequestParam int categoryId){
        return mainViewService.searchByCategory(keyword,categoryId);
    }

    @GetMapping("/rcmd")
    public boolean getIfrcmd(@RequestParam Long userId) {

        Optional<User> user  = userService.findById(userId);
        if(user.isPresent())
            return user.get().isIfRcmd();
        return false;
    }

//    @GetMapping("/stay/detailList")
//    public Stay getStayById(@RequestParam int stayId) {
//        return mainViewService.getStayList(stayId);
//    }
//
//
//    @GetMapping("/play/detailList")
//    public Stay getStayById(@RequestParam int stayId) {
//        return mainViewService.getStayList(stayId);
//    }

}
