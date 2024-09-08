package com.example.ai_jeju.controller;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.AlbumResponse;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.UserRepository;
import com.example.ai_jeju.service.AlbumService;
import com.example.ai_jeju.service.ChildService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChildController {


    @Autowired
    private AlbumService albumService;

    @Autowired
    private ChildService childService;

    @Autowired
    private UserRepository userRepository;

    private final TokenProvider tokenProvider;

    public ChildController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/albums")
    public ResponseDto getmainAlbums(@RequestHeader(value = "Authorization") String token){

        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                return ResponseUtil.SUCCESS("사진첩 메인리스트 조회에 성공하였습니다", childService.getMainAlbums(userId));
            } else {
                return ResponseUtil.ERROR("유효하지 않은 토큰입니다.", null);
            }
        }else{
            return ResponseUtil.ERROR("존재하지 않는 회원입니다.", null);
        }

    }

    @GetMapping("/child")
    public ResponseDto getMyChild(@RequestHeader(value = "Authorization") String token){

        if (token != null) {
            String accessToken = token.replace("Bearer ", "");
            if (tokenProvider.validToken(accessToken)) {
                Long userId = tokenProvider.getUserId(accessToken);
                return ResponseUtil.SUCCESS("사진첩 메인리스트 조회에 성공하였습니다", childService.getMainAlbums(userId));
            } else {
                return ResponseUtil.ERROR("유효하지 않은 토큰입니다.", null);
            }
        }else{
            return ResponseUtil.ERROR("존재하지 않는 회원입니다.", null);
        }

    }









}
