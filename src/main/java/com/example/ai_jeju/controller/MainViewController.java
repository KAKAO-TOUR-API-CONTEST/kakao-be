package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.Store;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.FilterDto;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.service.MainVIewService;
import com.example.ai_jeju.service.UserService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.v3.core.util.Json;
import org.openapitools.jackson.nullable.JsonNullable;
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
                return ResponseUtil.SUCCESS("비회원 상세 조회에 성공하였습니다.", mainViewService.getDetailList(storeId));
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
                return ResponseUtil.SUCCESS("비회원 메인 조회에 성공하였습니다.", mainViewService.getMainList());
            } catch (Exception e) {
                return ResponseUtil.ERROR(e.getMessage(), null);
            }
        }
    }

    //카테고리 아이디
    @GetMapping("/mainList/category")
    public ResponseDto getListByCategory(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam int categoryId){
        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("메인리스트 조회에 성공하였습니다.", mainViewService.getListByCategory(userId,categoryId));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {
                return ResponseUtil.ERROR("토큰 유효성 문제가 발생하였습니다.", null);
            }
        }
        return ResponseUtil.SUCCESS("메인리스트 조회에 성공하였습니다.", mainViewService.getListByCategory(categoryId));
    }

    @GetMapping("/searchList")
    public ResponseDto getListBySearch(@RequestHeader(value = "Authorization", required = false) String token,@RequestParam String keyword){
        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("검색에 성공하였습니다.", mainViewService.getListBySearch(userId,keyword));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {
                return ResponseUtil.ERROR("검색에 문제가 발생하였습니다.", null);
            }
        }else{
            return ResponseUtil.SUCCESS("비회원 검색어 조회에 성공하였습니다.",mainViewService.getListBySearch(keyword));
        }


    }

    @GetMapping("/searchList/category")
    public ResponseDto searchByCategory(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam String keyword, @RequestParam int categoryId){

        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("회원 검색어 카테고리 정렬에 성공하였습니다.", mainViewService.searchByCategory(userId,keyword,categoryId));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {
                return ResponseUtil.ERROR("검색어 카테고리 정렬에 문제가 발생하였습니다.", null);
            }
        }else{
            return ResponseUtil.SUCCESS("비회원 검색어 카테고리 정렬에 성공하였습니다.",mainViewService.searchByCategory(keyword,categoryId));
        }

    }

    @GetMapping("/rcmd")
    public boolean getIfrcmd(@RequestParam Long userId) {

        Optional<User> user  = userService.findById(userId);
        if(user.isPresent())
            return user.get().isIfRcmd();
        return false;
    }


    @GetMapping("/main")
    public ResponseDto getFiltering(@RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(required = false) Boolean parking,
            @RequestParam(required = false) Boolean strollerVar,
            @RequestParam(required = false) Boolean kidsZone,
            @RequestParam(required = false) Boolean playground,
            @RequestParam(required = false) Boolean babySpareChair,
            @RequestParam(required = false) Boolean rcmd,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) String keyword) {

        FilterDto filterDto = FilterDto.builder()
                .parking(JsonNullable.of(parking))
                .strollerVal(JsonNullable.of(strollerVar))
                .KidsZone(JsonNullable.of(kidsZone))
                .playground(JsonNullable.of(playground))
                .babySpareChair(JsonNullable.of(babySpareChair))
                .rcmd(JsonNullable.of(rcmd))
                .categoryId(JsonNullable.of(category))
                .keyword(JsonNullable.of(keyword))
                .build();

        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                try {
                    return ResponseUtil.SUCCESS("회원 조회에 성공하였습니다.", mainViewService.getMain(filterDto,userId));
                } catch (Exception e) {
                    return ResponseUtil.ERROR(e.getMessage(), null);
                }
            } else {

                return ResponseUtil.ERROR("유효하지 않은 사용자입니다.", null);
            }
        }else{
            return ResponseUtil.SUCCESS("비회원 조회에 성공하였습니다.",mainViewService.getMain(filterDto));
        }


    }

}
