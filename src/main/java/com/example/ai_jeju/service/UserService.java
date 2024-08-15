package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.RefreshToken;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.ChildRequest;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.generator.NickNameGenerator;
import com.example.ai_jeju.handler.SignUpHandler;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.RefreshTokenRepository;
import com.example.ai_jeju.repository.UserRepository;
import com.example.ai_jeju.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.example.ai_jeju.handler.SignUpHandler.REFRESH_TOKEN_COOKIE_NAME;


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
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    //private SignUpHandler signUpHandler = new SignUpHandler();

    // DB에 회원이 있을 때 -> 기존 회원일 경우
    public Object checkIfUser(String email){

        // 이미 가입한 회원인지 확인한다.
        Optional<User> existingUser = userRepository.findByEmail(email);

        /*--------------------------------------------------------------------------------------------------*/
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            String accessToken = tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
            return accessToken;
        }
        /*--------------------------------------------------------------------------------------------------*/
        // db에 회원정보 없음 -> 새로운 회원 추가
        else{
            return false;
        }
    }


    public String signUp( SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response) {

        String nick = signUpRequest.getNickname();
        if(nick==null){
            nick = new NickNameGenerator().getNickname();
        }
        // Save new user using builder pattern
        User newUser = User.builder()
                .name(signUpRequest.getName())
                .nickname(nick)
                .provider(signUpRequest.getProvider())
                .email(signUpRequest.getEmail())
                .snsprofile(signUpRequest.getProfile())
                .provider(signUpRequest.getProvider())
                .build();

        String accessToken = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
        /*-------------------------------------------*/
        //동반아동
        List<ChildRequest> childList = signUpRequest.getChild();
        for(int i=0; i<childList.size(); i++){
            Child child = Child.builder()
                    //유저 아이디의 값 그대로 주기.
                    .userId(newUser.getId())
                    .childName(childList.get(i).getChildName())
                    .birthDate(childList.get(i).getBirthDate())
                    .gender(childList.get(i).getGender())
                    .build();
            childRepository.save(child);
        }

        userRepository.save(newUser);
        String refresh_token = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);

        saveRefreshToken(newUser.getId(), refresh_token);
        addRefreshTokenToCookie(request,response,refresh_token);
        return  accessToken;

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

    public String successHadler(HttpServletRequest request,
                                HttpServletResponse response, SignUpRequest signUpRequest) throws IOException {

        String nick = signUpRequest.getNickname();
        if(nick==null){
            nick = new NickNameGenerator().getNickname();
        }
        // Save new user using builder pattern
        User newUser = User.builder()
                .name(signUpRequest.getName())
                .nickname(nick)
                .provider(signUpRequest.getProvider())
                .email(signUpRequest.getEmail())
                .snsprofile(signUpRequest.getProfile())
                .provider(signUpRequest.getProvider())
                .build();


        String accessToken = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
        /*-------------------------------------------*/
        //동반아동
        List<ChildRequest> childList = signUpRequest.getChild();
        for(int i=0; i<childList.size(); i++){
            Child child = Child.builder()
                    //유저 아이디의 값 그대로 주기.
                    .userId(newUser.getId())
                    .childName(childList.get(i).getChildName())
                    .birthDate(childList.get(i).getBirthDate())
                    .gender(childList.get(i).getGender())
                    .build();
            childRepository.save(child);
        }

        userRepository.save(newUser);
        String refresh_token = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);

        saveRefreshToken(newUser.getId(), refresh_token);
        addRefreshTokenToCookie(request,response,refresh_token);
        return  accessToken;
    }


    //생성된 리프레시 토큰을 전달받아 데이터베이스 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    //생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request,
                                         HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
        System.out.println("addRefreshToken 동작");
    }



}
