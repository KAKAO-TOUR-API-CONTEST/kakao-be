package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Album;
import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.ScheduleItem;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.ChildResponseDto;
import com.example.ai_jeju.dto.ScheduleItemDto;
import com.example.ai_jeju.repository.AlbumRepository;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.ScheduleItemRepository;
import com.example.ai_jeju.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChildService {
    LocalDate today = LocalDate.now();
    int year = today.getYear();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    public List<ChildResponseDto> getMainAlbums(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            List<ChildResponseDto> childResponseDtos = new ArrayList<>();
            List<Child> childs = childRepository.findAllByUser(user.get());
            for(Child child : childs){
                List<ScheduleItem> scheduleItems = scheduleItemRepository.findAllByChild(child);
                List<ScheduleItemDto> scheduleItemDtos = new ArrayList<>();
                List<Album> albums = albumRepository.findAllByChild(child);
                for(Album album : albums){
                    ScheduleItemDto scheduleItemDto = ScheduleItemDto.builder()
                            .scheduleTitle(album.getAlbumTitle())
                            .year(album.getRgtDate().split("-")[0])
                            .month(album.getRgtDate().split("-")[1])
                            .day(album.getRgtDate().split("-")[2])
                            .build();
                    scheduleItemDtos.add(scheduleItemDto);
                }
                ChildResponseDto childResponseDto = ChildResponseDto.builder()
                        .childId(child.getChildId())
                        .childName(child.getChildName())
                        .birthDate(child.getBirthDate())
                        .scheduleItems(scheduleItemDtos)
                        .age(year- Integer.parseInt(child.getBirthDate().split("\\.")[0])-1)
                        .profieImg(child.getChildProfile())
                        .build();
                childResponseDtos.add(childResponseDto);
            }

            return childResponseDtos;
        }else{
            return null;
        }




    }
}
