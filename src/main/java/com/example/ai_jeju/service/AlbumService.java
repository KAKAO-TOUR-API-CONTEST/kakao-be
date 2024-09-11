package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.*;
import com.example.ai_jeju.dto.AddAlbumRequest;
import com.example.ai_jeju.dto.AlbumItemDto;
import com.example.ai_jeju.dto.AlbumOptionDto;
import com.example.ai_jeju.dto.AlbumResponse;
import com.example.ai_jeju.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumItemRepository albumItemRepository;
    @Autowired
    private AlbumOptionRepository albumOptionRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    public List<AlbumResponse> getAlbumList(Long childId){
        Optional<Child> childOptional = childRepository.findByChildId(childId);
        if(childOptional.isPresent()){
            List<Album> albums = albumRepository.findAllByChild(childOptional.get());

            List<AlbumResponse> albumResponses = new ArrayList<>();

            for(Album album : albums){
                AlbumResponse albumResponse = AlbumResponse.builder()
                        .albumId(album.getAlbumId())
                        .title(album.getAlbumTitle())
                        .repImgSrc(album.getRepImgSrc())
                        .build();
                albumResponses.add(albumResponse);
            }
            return albumResponses;
        }else{
            return null;
        }
    }

    public void addAlbum(AddAlbumRequest addAlbumRequest){

        Optional<Child> child = childRepository.findByChildId(addAlbumRequest.getChildId());
        if(child.isPresent()){
            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            // 포맷 정의 (예: yyyy-MM-dd HH:mm:ss)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 문자열로 변환
            String rgtDate = now.format(formatter);

            //제목이 비어있을수도 있으니까 예상해서 -> title이 비었다면 현재 시간으로
            String albumTitle = addAlbumRequest.getAlbumTitle().orElse(rgtDate);
            //설명이 비어있을수도 있으니까 예상해서 -> Desc가 비었다면 빈 문자열로
            String albumDesc = addAlbumRequest.getAlbumDesc().orElse("");

            Album album = Album.builder()
                    .child(child.get())
                    .albumDesc(albumDesc)
                    //가장 첫번째 이미지 사진을 가져와서 대표사진으로 설정한다.
                    .repImgSrc(addAlbumRequest.getAlbumItemDtos().get(0).getImgSrc())
                    .albumTitle(albumTitle)
                    .rgtDate(rgtDate)
                    .build();

            List<AlbumItemDto> albumItemDtos = addAlbumRequest.getAlbumItemDtos();

            String year = rgtDate.split("-")[0];
            String month = rgtDate.split("-")[1];
            String day = rgtDate.split("-")[2];

            //앨범에 저장한다.
            Album savedAlbum = albumRepository.save(album);

            AlbumOptionDto albumOptionDto = addAlbumRequest.getAlbumOptionDto();

            AlbumOption albumOption = AlbumOption.builder()
                    .album(savedAlbum)
                    .op1(albumOptionDto.isOp1())
                    .op2(albumOptionDto.isOp2())
                    .op3(albumOptionDto.isOp3())
                    .op4(albumOptionDto.isOp4())
                    .op5(albumOptionDto.isOp5())
                    .op6(albumOptionDto.isOp6())
                    .op7(albumOptionDto.isOp7())
                    .op8(albumOptionDto.isOp8())
                    .op9(albumOptionDto.isOp9())
                    .op10(albumOptionDto.isOp10())
                    .op11(albumOptionDto.isOp11())
                    .op12(albumOptionDto.isOp12())
                    .build();
            albumOptionRepository.save(albumOption);

            for(AlbumItemDto albumItemDto : albumItemDtos){
                AlbumItem albumItem = AlbumItem.builder()
                        .imgSrc(albumItemDto.getImgSrc())
                        .album(savedAlbum)
                        .build();
                albumItemRepository.save(albumItem);
            }

            // 3. 스케쥴 저장하기.
            ScheduleItem scheduleItem = ScheduleItem.builder()
                    .scheduleItemId(savedAlbum.getAlbumId())
                    .year(year)
                    .month(month)
                    .day(day)
                    .child(child.get())
                    .build();

            scheduleItemRepository.save(scheduleItem);

        }else{
            return;
        }
    }
}
