package com.example.ai_jeju.controller;


import com.example.ai_jeju.config.UUIDOneTimeTokenManager;
import com.example.ai_jeju.dto.*;
import com.example.ai_jeju.exception.UserNotFoundException;
import com.example.ai_jeju.service.AlbumService;
import com.example.ai_jeju.service.UUIDTokenService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    UUIDOneTimeTokenManager tokenManager = new UUIDOneTimeTokenManager();
    UUIDTokenService UUIDtokenService = new UUIDTokenService(tokenManager);

    @PostMapping("")
    public ResponseDto getAlbumList(@RequestBody AddAlbumRequest addAlbumRequest){

      try{
          albumService.addAlbum(addAlbumRequest);
          return ResponseUtil.SUCCESS("사진첩 등록에 성공하셨습니다",null);
      } catch (Exception e){
          return ResponseUtil.FAILURE(e.getMessage(),null);
      }
    }

    @PutMapping("/modify")
    public ResponseDto modifyAlbum(@RequestBody ModifyAlbumRequest modifyAlbumRequest){

        try{
            albumService.updateAlbum(modifyAlbumRequest);
            return ResponseUtil.SUCCESS("사진첩 수정에 성공하셨습니다",null);
        } catch (Exception e){
            return ResponseUtil.FAILURE(e.getMessage(),null);
        }
    }


    @GetMapping("")
    public ResponseDto getAlbumList(@RequestParam Long childId ,String rgtDate){
        List<AlbumListResponse> albumResponses =albumService.getAlbumList(childId,rgtDate);
        return ResponseUtil.SUCCESS("사진첩 조회이 성공하셨습니다",albumResponses);
    }

    @GetMapping("/detail")
    public ResponseDto getDetailAlbumList(@RequestParam Long albumId){

        AlbumDetailResponse albumDetailResponse = albumService.getDetailAlbumList(albumId);
        return ResponseUtil.SUCCESS("사진첩 조회이 성공하셨습니다",albumDetailResponse);

    }

    @GetMapping("/shares")
    public ResponseDto getLinkforSharing(){
        // TokenService를 이용하여 토큰 생성
        String token = UUIDtokenService.createToken();
        return ResponseUtil.SUCCESS("임시 토큰",token);
    }

    @GetMapping("/schedule")
    public ResponseDto getScheculeListByChild(@RequestParam Long childId){
        try{
            return ResponseUtil.SUCCESS("아이의 캘린더 날짜 목록",albumService.getScheculeListByChild(childId));
        }catch(Exception e){
            return ResponseUtil.ERROR("아이의 캘린더 날짜 목록을 조회하는 실패하였습니다.",e);
        }
    }


}
