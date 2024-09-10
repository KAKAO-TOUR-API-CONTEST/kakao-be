package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.*;
import com.example.ai_jeju.exception.UserNotFoundException;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static graphql.introspection.IntrospectionQueryBuilder.build;

@Service
public class MyJejuService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private S3Service s3Service;
    public static int calculateMonths(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(birthDate, formatter);
        LocalDate currentDate = LocalDate.now();
        // 생년월일부터 현재까지의 개월 수 계산
        int months = (int) ChronoUnit.MONTHS.between(date, currentDate);
        return months;
    }

    LocalDate today = LocalDate.now();
    int year = today.getYear();

    //마이페이지 회원 한명 id로 찾기
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateNickname(Long id, String nickname) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(nickname);
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(Long id, String profileimg) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileImg(profileimg); // S3에 업로드된 이미지의 URL을 프로필 이미지로 설정
        userRepository.save(user);
    }

    //프로필 이미지 반환
    public String getProfileUrl(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return user.getProfileImg(); // 프로필 이미지 URL 반환
    }

    public void deleteProfileImage(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileImg(null);
            userRepository.save(user);
        } else {
            throw new NoSuchElementException("not found");
        }
    }

    public void updateUser(Long userId, ModifyMyPageRequest modifyMyPageRequest) {
        //수정당할 애 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // 닉네임 업데이트
        // 수정된 사용자 빌드
        User updatedUser = User.builder()
                .id(user.getId())  // 기존 ID 유지
                .nickname(modifyMyPageRequest.getNickname().orElse(user.getNickname()))  // 닉네임 수정
                .phoneNum(modifyMyPageRequest.getPhoneNum().orElse(user.getPhoneNum()))  // 전화번호 수정
                // 추가적으로 수정할 필드가 있다면 여기에 추가
                .build();

        // 변경된 사용자 정보를 저장
        userRepository.save(updatedUser);

        // 변경된 사용자 정보를 저장
        userRepository.save(user);
    }



    public MyJejuResponse getMyJeju(Long userId){
        // 나 찾기
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // 내 아이 찾기
        List<Child> childs = childRepository.findAllByUser(user);

        MyJejuMyDto myJejuMyDto = MyJejuMyDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .imgSrc(user.getProfileImg())
                .numOfChild(childs.size())
                .rgtDate(user.getRgtDate())
                .nickname(user.getNickname())
                .build();

        List<MyJejuChildDto> myJejuChildDtos = new ArrayList<>();



        for(int i=0; i<childs.size(); i++){
            Child child = childs.get(i);
            MyJejuChildDto myJejuChildDto = MyJejuChildDto.builder()
                    .childId(child.getChildId())
                    .relation(child.getRealtion())
                    .birthDate(child.getBirthDate())
                    .months(calculateMonths(child.getBirthDate()))
                    .order(i+1)
                    .name(child.getChildName())
                    .imgSrc(childs.get(i).getChildProfile())
                    .age(year- Integer.parseInt(child.getBirthDate().split("\\.")[0])-1)
                    .build();
            myJejuChildDtos.add(myJejuChildDto);
        }

        MyJejuResponse myJejuResponse = MyJejuResponse.builder()
                        .myJejuMyDto(myJejuMyDto)
                .myJejuChildDtos(myJejuChildDtos)
                .build();

        return myJejuResponse;

    }

    public ChildDetailResponse getMyChild(Long childId){
        Optional<Child> child = childRepository.findByChildId(childId);
        if(child.isPresent()){
            Child registeredChild = child.get();
            ChildDetailResponse childDetailResponse = ChildDetailResponse.builder()
                    .name(registeredChild.getChildName())
                    .birthDate(registeredChild.getBirthDate())
                    .age((year-1)- Integer.parseInt(registeredChild.getBirthDate().split("\\.")[0]))
                    .gender(registeredChild.getGender())
                    .months(calculateMonths(registeredChild.getBirthDate()))
                    .profileImg(registeredChild.getChildProfile())
                    .relation(registeredChild.getRealtion())
                    .build();
            return childDetailResponse;
        }
        return null;

    }

    @Transactional
    public void deleteMyChild(Long childId){
        Optional<Child> child = childRepository.findByChildId(childId);
        childRepository.delete(child.get());
    }
}
