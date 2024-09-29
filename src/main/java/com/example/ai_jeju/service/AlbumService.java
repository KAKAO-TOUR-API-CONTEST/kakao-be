package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.*;
import com.example.ai_jeju.dto.*;
import com.example.ai_jeju.exception.UserNotFoundException;
import com.example.ai_jeju.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Transactional
    public List<AlbumListResponse> getAlbumList(Long childId, String rgtDate){
        Optional<Child> childOptional = childRepository.findByChildId(childId);
        if(childOptional.isPresent()){
            List<Album> albums = albumRepository.findAllByChildAndRgtDate(childOptional.get(),rgtDate);
            List<AlbumListResponse> albumResponses = new ArrayList<>();

            for(Album album : albums){
                //albumOption 찾기.
                List<String> options = new ArrayList<>();
                Optional<AlbumOption> optionalAlbumOption = albumOptionRepository.findByAlbum(album);
                AlbumOption albumOption = optionalAlbumOption.orElse(null);
                //albumOption이 존재하지 않은 경우도 있으니까.
                if(albumOption.isOptionalPet()) options.add("동물");
                if(albumOption.isOptionalFriend()) options.add("친구");
                if(albumOption.isOptionalFamily()) options.add("가족");
                if(albumOption.isOptionalMorning()) options.add("아침");
                if(albumOption.isOptionalAfterNoon()) options.add("낮");
                if(albumOption.isOptionalNight()) options.add("저녁");
                if(albumOption.isOptionalDining()) options.add("식사");
                if(albumOption.isOptionalSnack()) options.add("간식");
                if(albumOption.isOptionalPlay()) options.add("놀이");
                if(albumOption.isOptionalStudy()) options.add("공부");
                if(albumOption.isOptionalExperience()) options.add("체험");
                if(albumOption.isOptionalWalk()) options.add("산책");

                AlbumListResponse albumListResponse = AlbumListResponse.builder()
                        .albumId(album.getAlbumId())
                        .albumOptions(options)
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

            String rgtDate = addAlbumRequest.getRgtDate();
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

    @Transactional
    public void updateAlbum(ModifyAlbumRequest modifyAlbumRequest){

            //수정당할 애 찾기
        Album album = albumRepository.findByAlbumId(modifyAlbumRequest.getAlbumId()).orElseThrow(() -> new UserNotFoundException("not found album"));

            // 닉네임 업데이트
            // 수정된 사용자 빌드
        Album updatedAlbum = Album.builder()
                .albumId(modifyAlbumRequest.getAlbumId()) // 기존 ID 유지
                .albumTitle(modifyAlbumRequest.getAlbumTitle().orElse(album.getAlbumTitle()))
                .rgtDate(album.getRgtDate())
                .repImgSrc(album.getRepImgSrc())
                .child(album.getChild())// 닉네임 수정
                .albumDesc(modifyAlbumRequest.getAlbumDesc().orElse(album.getAlbumDesc()))  // 전화번호 수정
                    // 추가적으로 수정할 필드가 있다면 여기에 추가
                    .build();

            // 변경된 사용자 정보를 저장
            albumRepository.save(updatedAlbum);

    }

    @Transactional
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


    public ScheduleResponse getScheculeListByChild(Long childId){

        Optional<Child> childOptional = childRepository.findByChildId(childId);
        List<String> schedules = new ArrayList<>();

        if(childOptional.isPresent()) {
            List<Album> albums = albumRepository.findAllByChild(childOptional.get());
            for(Album album : albums){
                schedules.add(album.getRgtDate());
            }
        }

            ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                    .schedules(schedules)
                    .build();

            return scheduleResponse;


    }


}
