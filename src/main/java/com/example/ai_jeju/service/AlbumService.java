package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.*;
import com.example.ai_jeju.dto.*;
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
    private UserRepository userRepository;

    @Autowired
    private AlbumItemRepository albumItemRepository;

    @Autowired
    private AlbumOptionRepository albumOptionRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    public List<AlbumListResponse> getAlbumList(Long childId, String rgtDate){
        Optional<Child> childOptional = childRepository.findByChildId(childId);
        if(childOptional.isPresent()){
            List<Album> albums = albumRepository.findAllByChildAndRgtDate(childOptional.get(),rgtDate);
            List<AlbumListResponse> albumResponses = new ArrayList<>();
            for(Album album : albums){

                //albumOption 찾기.
                Optional<AlbumOption> OptionalalbumOption = albumOptionRepository.findByAlbum(album);
                AlbumOption albumOption;
                AlbumOptionDto albumOptionDto;
                //albumOption이 존재하지 않은 경우도 있으니까.
                if(OptionalalbumOption.isPresent()){
                    albumOption = OptionalalbumOption.get();
                    albumOptionDto = albumOption.toDto();
                }else{
                    albumOptionDto = null;
                }

                AlbumListResponse albumListResponse = AlbumListResponse.builder()
                        .albumId(album.getAlbumId())
                       // .albumOption(albumOptionDto)
                        .title(album.getAlbumTitle())
                        .repImgSrc(album.getRepImgSrc())
                        .build();

                albumResponses.add(albumListResponse);
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
                    .optionalPet(albumOptionDto.getOptionalPet().orElse(false))
                    .optionalFriend(albumOptionDto.getOptionalFriend().orElse(false))
                    .optionalFamily(albumOptionDto.getOptionalFamily().orElse(false))
                    .optionalMorning(albumOptionDto.getOptionalMorning().orElse(false))
                    .optionalAfterNoon(albumOptionDto.getOptionalAm().orElse(false))
                    .optionalNight(albumOptionDto.getOptionalPm().orElse(false))
                    .optionalDining(albumOptionDto.getOptionalDining().orElse(false))
                    .optionalSnack(albumOptionDto.getOptionalSnack().orElse(false))
                    .optionalPlay(albumOptionDto.getOptionalPlay().orElse(false))
                    .optionalStudy(albumOptionDto.getOptionalStudy().orElse(false))
                    .optionalExperience(albumOptionDto.getOptionalExperience().orElse(false))
                    .optionalWalk(albumOptionDto.getOptionalWalk().orElse(false))
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

    public AlbumDetailResponse getDetailAlbumList(Long albumId){

        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if(optionalAlbum.isPresent()){
            Album album = optionalAlbum.get();
            List<String> imgSrcDtos = new ArrayList<>();
            //album Item list
            List<AlbumItem> albumItems = albumItemRepository.findByAlbum(album);
            for(AlbumItem albumItem : albumItems ){
                imgSrcDtos.add(albumItem.getImgSrc());
            }
            AlbumDetailResponse albumDetailResponse = AlbumDetailResponse.builder()
                    .childName(album.getChild().getChildName())
                    .AlbumTitle(album.getAlbumTitle())
                    .AlbumDesc(album.getAlbumDesc())
                    .rgtDate(album.getRgtDate())
                    .imgSrcDtos(imgSrcDtos)
                    .build();
            return albumDetailResponse;
        }else{
            //album없을때
            return null;
        }

    }


}
