package com.example.ai_jeju.service;

import com.example.ai_jeju.domain.*;
import com.example.ai_jeju.dto.ChildRequest;
import com.example.ai_jeju.dto.MyPageResponse;
import com.example.ai_jeju.dto.WithdrawRequest;
import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.generator.NickNameGenerator;
import com.example.ai_jeju.handler.SignUpHandler;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.*;
import com.example.ai_jeju.util.CookieUtil;
import com.example.ai_jeju.util.ResponseDto;
import com.example.ai_jeju.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static com.example.ai_jeju.handler.SignUpHandler.ACCESS_TOKEN_COOKIE_NAME;
//import static com.example.ai_jeju.handler.SignUpHandler.REFRESH_TOKEN_COOKIE_NAME;


@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private StoreRepository storeRepository;
    /**
     * login/signu up flow-1
     * checkIfUser : 기존 회원여부 확인
     * 기존 회원이라면 객체 (아이디만) 반환 , AccessToken 쿠키로 발급
     */
    public ResponseDto checkIfUser(String email, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        //Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(email);
        //String refresh_token = refreshToken.get().getRefresh_token();
        //refresh_token.get
        Map<String, Object> result = new HashMap<>();

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);

            return ResponseUtil.SUCCESS("로그인 완료되었습니다.", accessToken);
           /*
            result.put("statusCode", 1000);
            result.put("message", "existinguser");
            result.put("data", Map.of(
                    "userId", user.getId(),
                    "accessToken", accessToken
            ));*/
        } else {

            return ResponseUtil.FAILURE("등록되어 있지 않은 유저입니다.", null);
        }


    }

    public void registerChild(Long userId, ChildRequest childRequest){

        Child child = Child.builder()
                // 유저 아이디의 값 그대로 주기.
                .userId(userId)
                .childName(childRequest.getChildName())
                .birthDate(childRequest.getBirthDate())
                .gender(childRequest.getGender())
                .realtion(childRequest.getRelation())
                .build();

        childRepository.save(child);
    }

    /**
     * login/signu up flow-3
     * registerUser : 새로운 회원 DB 저장
     * 기존 회원이라면 객체 반환 , AccessToken 쿠키로 발급
     */
    public ResponseDto registerUser( SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response) {
        // 닉네임 없을 때 생성

        String nick = signUpRequest.getNickname();
        if(nick==null){
            //일단 닉네임을 생성해보고.
            nick = new NickNameGenerator().getNickname();
            //만약 중복된 닉네임이 없을 때까지 재생성한다.
            while(!userRepository.findUserByNickname(nick).isPresent()){
                nick = new NickNameGenerator().getNickname();
            }
        }
        // 가입일자
        LocalDate date = LocalDate.now();
        // Save new user using builder pattern
        User newUser = User.builder()
                .name(signUpRequest.getName())
                .nickname(nick)
                .provider(signUpRequest.getProvider())
                .email(signUpRequest.getEmail())
                .profileImg(signUpRequest.getProfileImg())
                .provider(signUpRequest.getProvider())
                .rgtDate(date.toString())
                .phoneNum(signUpRequest.getPhoneNum())
                .ifRcmd(signUpRequest.getIfRcmd())
                .build();
        /*-------------------------------------------*/

        // 1. 부모정보 저장하기
        userRepository.save(newUser);
        Optional<User> registerdUser = userRepository.findByEmail(newUser.getEmail());

        // 2. 동반아동 등록하기
        List<ChildRequest> childList = signUpRequest.getChild();
        for(int i=0; i<childList.size(); i++){
            Child child = Child.builder()
                    // 유저 아이디의 값 그대로 주기.
                    .userId(registerdUser.get().getId())
                    .childName(childList.get(i).getChildName())
                    .birthDate(childList.get(i).getBirthDate())
                    .gender(childList.get(i).getGender())
                    .realtion(childList.get(i).getRelation())
                    .build();

            childRepository.save(child);
        }

        String refresh_token = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
        String access_token = tokenProvider.generateToken(newUser, ACCESS_TOKEN_DURATION);
        // DB에 refreshToken 저장
        saveRefreshToken(newUser.getId(), refresh_token);
        // AccessToken 쿠키로 발급
        addAccessTokenToCookie(request,response, access_token);

        return  ResponseUtil.SUCCESS("로그인 완료되었습니다.", access_token);
    }

//    public List<Store> getRandomList(){
//        List<Store> RandomStores = new ArrayList<Store>();
//        StoreRepository.findById(Long.valueOf(1));
//    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(
                        ()-> new IllegalArgumentException("unexpected user"));
    }


    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }
    /**
     * withdroaw up flow
     * checkIfUser : 기존 회원여부 확인
     * 기존 회원이라면 객체 (아이디만) 반환 , AccessToken 쿠키로 발급
     */
    public String withDraw(WithdrawRequest withDrawRequest){
        String email = withDrawRequest.getEmail();
        String accessToken = withDrawRequest.getAccessToken();
        // 기본 빈 url
        String url ="";
        // email 기반으로 삭제할 user 객체 찾기
        // provider 추출하기
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

    //생성된 리프레시 토큰을 전달받아 데이터베이스 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }
    //생성된 액세스 토큰을 쿠키에 저장
    private void addAccessTokenToCookie(HttpServletRequest request,
                                         HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }
    public MyPageResponse getMyPage(Long userId){

        MyPageResponse myPageRes = new MyPageResponse();
        User user = userRepository.findById(userId).get();
        List<Child> childs = childRepository.findAllById(userId);
        // myPageResponse  : 응답 객체 만들기
        //System.out.println(childs.get(0).getChildName());
        myPageRes.setEmail(user.getEmail());
        myPageRes.setName(user.getName());
        myPageRes.setNickname(user.getNickname());
        myPageRes.setProfileImg(user.getProfileImg());
        myPageRes.setRgtDate(user.getRgtDate());
        myPageRes.setPhoneNum(user.getPhoneNum());
        myPageRes.setNumOfChilds(childs.size());
        return  myPageRes;
    }
}
