package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Album;
import com.example.ai_jeju.domain.AlbumItem;
import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.dto.AddAlbumRequest;
import com.example.ai_jeju.dto.AlbumItemDto;
import com.example.ai_jeju.dto.AlbumResponse;
import com.example.ai_jeju.repository.AlbumItemRepository;
import com.example.ai_jeju.repository.AlbumRepository;
import com.example.ai_jeju.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ChildRepository childRepository;

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
            Album album = Album.builder()
                    .child(child.get())
                    .albumDesc(addAlbumRequest.getAlbumDesc())
                    .repImgSrc(addAlbumRequest.getAlbumItemDtos().get(0).getImgSrc())
                    .albumTitle(addAlbumRequest.getAlbumTitle())
                    .build();

            List<AlbumItemDto> albumItemDtos = addAlbumRequest.getAlbumItemDtos();
            Album savedAlbum = albumRepository.save(album);
            for(AlbumItemDto albumItemDto : albumItemDtos){
                AlbumItem albumItem = AlbumItem.builder()
                        .imgSrc(albumItemDto.getImgSrc())
                        .album(savedAlbum)
                        .build();

                albumItemRepository.save(albumItem);
            }



        }else{
            return;
        }
    }
}
