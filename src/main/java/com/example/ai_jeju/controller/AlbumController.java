package com.example.ai_jeju.controller;


import com.example.ai_jeju.dto.AddAlbumRequest;
import com.example.ai_jeju.dto.AlbumDetailResponse;
import com.example.ai_jeju.dto.AlbumListResponse;
import com.example.ai_jeju.dto.AlbumResponse;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.service.AlbumService;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ChildRepository childRepository;


    @PostMapping("/album")
    public ResponseDto getAlbumList(@RequestBody AddAlbumRequest addAlbumRequest){

      try{
          albumService.addAlbum(addAlbumRequest);
          return ResponseUtil.SUCCESS("사진첩 등록에 성공하셨습니다",null);
      } catch (Exception e){
          return ResponseUtil.FAILURE(e.getMessage(),null);
      }
    }


    @GetMapping("/album")
    public ResponseDto getAlbumList(@RequestParam Long childId ,String rgtDate){
        List<AlbumListResponse> albumResponses =albumService.getAlbumList(childId,rgtDate);
        return ResponseUtil.SUCCESS("사진첩 조회이 성공하셨습니다",albumResponses);
    }

    @GetMapping("/album/detail")
    public ResponseDto getDetailAlbumList(@RequestParam Long albumId ){
        AlbumDetailResponse albumDetailResponse = albumService.getDetailAlbumList(albumId);
        return ResponseUtil.SUCCESS("사진첩 조회이 성공하셨습니다",albumDetailResponse);
    }




}
