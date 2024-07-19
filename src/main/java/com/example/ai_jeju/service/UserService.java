package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.generator.NickNameGenerator;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;


    @Autowired
    private ChildRepository childRepository;


    public String signUp(SignUpRequest signUpRequest) {

        // 이미 가입한 회원인지 확인한다.
        Optional<User> existingUserByEmail = userRepository.findByEmail(signUpRequest.getEmail());

        // DB에 회원이 있을 때 -> 기존 회원일 경우
        /*--------------------------------------------------------------------------------------------------*/
        if (existingUserByEmail.isPresent()) {
            User user = this.findByEmail(signUpRequest.getEmail());
            String token = tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
            //accessToken반환
            //여기 부분 바꿔야함.
            return token;
        }
        /*--------------------------------------------------------------------------------------------------*/
        // db에 회원정보 없음 -> 새로운 회원 추가
        else{
            //요청에서 들어온 닉네임
            String nick = signUpRequest.getNickname();
            if(nick==null){
                System.out.println("nickname is null");
                nick = new NickNameGenerator().getNickname();
            }


            // Save new user using builder pattern
            User newUser = User.builder()
                    .name(signUpRequest.getName())
                    .nickname(nick)
                    .provider(signUpRequest.getProvider())
                    .email(signUpRequest.getEmail())
                    .profile(signUpRequest.getProfile())
                    .provider(signUpRequest.getProvider())
                    .build();


            userRepository.save(newUser);//User정보 저장


            //잘 받아오는지 확인했다.

            List<Child> childList = signUpRequest.getChild();
            //일단 한번 해보자.

            for(int i=0; i<childList.size(); i++){
                Child child = Child.builder()
                        .userId(newUser.getId())
                        .childName(childList.get(i).getChildName())
                        .birthDate(childList.get(i).getBirthDate())
                        .gender(childList.get(i).getGender())
                        .build();

                childRepository.save(child);
            }









            /*여기서 부터는 잘 모르겠는 부분 */
            //리프레시 토큰 발급 새로운 유저 객체 + 리프레시 토큰 duration
            String refreshToken1 = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);




            return refreshToken1;
        }
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(
                        ()-> new IllegalArgumentException("unexpected user"));
    }





    //회원 탈퇴하기
    public String withDraw(WithdrawRequest withDrawRequest){

        String email = withDrawRequest.getEmail();
        String accessToken = withDrawRequest.getAccessToken();
        //기본 빈 url
        String url ="";
        //provider 추출하기
        //email 기반으로 삭제할 user 객체 찾기
        User delUser = this.findByEmail(withDrawRequest.getEmail());
        String provider = delUser.getProvider();
        /*--------------------------------------------------------------------------------------------------*/
        switch (provider){
            case "kakao":
                url = "https://kapi.kakao.com/v1/user/unlink";
                //헤더 만들기
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.set("Authorization", "Bearer " + accessToken);

                //전달할 Header 기반 HttpEntity 만들기
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                ResponseEntity<String> result = ResponseEntity.status(response.getStatusCode()).body(response.getBody());

                System.out.println("카카오 탈퇴 결과값 :"+result);
                //맞을때
                if(result.equals("200")){
                    userRepository.delete(delUser);
                    return("delete success");
                }
                //아니면 그냥 break..
                else{
                    return("delete fail");
                }

                /*--------------------------------------------------------------------------------------------------*/
            case "google":
                url  = "https://accounts.google.com/o/oauth2/revoke?token="+accessToken;
                ResponseEntity<String> googleRes = restTemplate.getForEntity(url, String.class);
                ResponseEntity<String> googleResult = ResponseEntity.status(googleRes.getStatusCode()).body(googleRes.getBody());
                System.out.println("구글 탈퇴 결과값 :"+googleResult);

                /*--------------------------------------------------------------------------------------------------*/

                //맞을때
                if(googleResult.equals("200")){
                    userRepository.delete(delUser);
                    return("delete success");
                }

                //아니면 그냥 break..
                else{
                    return("delete fail");
                }

        }

        return "result";
    }
}
