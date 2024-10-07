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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

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
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private StoreRepository storeRepository;

    /**
     * login/signu up flow-1
     * checkIfUser : 기존 회원여부 확인
     * 기존 회원이라면 객체 (아이디만) 반환 , AccessToken 쿠키로 발급
     */
    public String[] checkIfUser(String email, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        String[] result = new String[2];
        if (result == null) {
            return null;
        }

        if (existingUser.isPresent() && existingUser.get().isValid()) {
            User user = existingUser.get();
            String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
            Date expiredDate = tokenProvider.getExpiredDate(accessToken);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // Date를 String으로 변환
            String dateString = formatter.format(expiredDate);
            result[0] = accessToken;
            result[1] = dateString;
            return result;
        } else {

            return null;
        }
    }

    public void registerChild(Long userId, ChildRequest childRequest) {
        Child child = Child.builder()
                .user(userRepository.findById(userId).get())
                .childName(childRequest.getChildName())
                .birthDate(childRequest.getBirthDate())
                .childProfile(childRequest.getChildProfile())
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
    public ResponseDto registerUser(SignUpRequest signUpRequest, HttpServletRequest request, HttpServletResponse response) {
        // 닉네임 없을 때 생성
        Optional<User> existingUser = userRepository.findByEmail(signUpRequest.getEmail());

        if (!existingUser.isPresent()) {
            String nick = signUpRequest.getNickname();
            if (nick == null) {
                //일단 닉네임을 생성해보고.
                nick = new NickNameGenerator().getNickname();
                //만약 중복된 닉네임이 없을 때까지 재생성한다.
                while (userRepository.findUserByNickname(nick).isPresent()) {
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
                    .valid(true)
                    .build();
            /*-------------------------------------------*/

            // 1. 부모정보 저장하기
            userRepository.save(newUser);
            Optional<User> registerdUser = userRepository.findByEmail(newUser.getEmail());

            // 2. 동반아동 등록하기
            List<ChildRequest> childList = signUpRequest.getChild();
            for (int i = 0; i < childList.size(); i++) {
                Child child = Child.builder()
                        // 유저 아이디의 값 그대로 주기.
                        .user(registerdUser.get())
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
            addAccessTokenToCookie(request, response, access_token);

            return ResponseUtil.SUCCESS("로그인 완료되었습니다.", access_token);
        } else {
            // 한번 탈퇴한 회원일 경우에, 혹은 있거나?
            //이미 있는 회원일 때
            if(existingUser.get().isValid()){
                return ResponseUtil.FAILURE("이미 가입된 회원입니다.", "");
            }else{
                // 한번 탈퇴한 회원일 경우에
                User user = existingUser.get();
                String nick = signUpRequest.getNickname();
                if (nick == null) {
                    //일단 닉네임을 생성해보고.
                    nick = new NickNameGenerator().getNickname();
                    //만약 중복된 닉네임이 없을 때까지 재생성한다.
                    while (userRepository.findUserByNickname(nick).isPresent()) {
                        nick = new NickNameGenerator().getNickname();
                    }
                }
                // 가입일자
                LocalDate date = LocalDate.now();
                // Save new user using builder pattern
                userRepository.updateUser(user.getId(), true, nick, signUpRequest.getProfileImg(),
                        signUpRequest.getProvider(),
                        date.toString(), signUpRequest.getPhoneNum(), signUpRequest.getIfRcmd(),
                        signUpRequest.getSelectedCk1(), signUpRequest.getSelectedCk2());
                /*-------------------------------------------*/

                // 1. 부모정보 저장하기
                //User newUser = userRepository.save(requestedUser);
                //Optional<User> registerdUser = userRepository.findByEmail(newUser.getEmail());
                // 2. 동반아동 등록하기
                List<ChildRequest> childList = signUpRequest.getChild();
                for (int i = 0; i < childList.size(); i++) {
                    Child child = Child.builder()
                            // 유저 아이디의 값 그대로 주기.
                            .user(user)
                            .childName(childList.get(i).getChildName())
                            .birthDate(childList.get(i).getBirthDate())
                            .gender(childList.get(i).getGender())
                            .realtion(childList.get(i).getRelation())
                            .build();

                    childRepository.save(child);
                }

                String refresh_token = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
                String access_token = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
                // DB에 refreshToken 저장
                saveRefreshToken(user.getId(), refresh_token);
                // AccessToken 쿠키로 발급
                addAccessTokenToCookie(request, response, access_token);

                return ResponseUtil.SUCCESS("로그인 완료되었습니다.", access_token);
            }
        }
    }

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

    @Transactional
    public void withDraw(String accessToken, String email){
        if(tokenProvider.validToken(accessToken)){
            Optional<User> delUser = userRepository.findById(tokenProvider.getUserId(accessToken));
            if(delUser.isPresent()){
                User user = delUser.get();
                userRepository.updateUserByValid(user.getId(), false); // valid 항목을 변경하는 메서드 호출
                // 변경사항 저장
                // soft-delete
            }else{
                // 존재하지 않는 회원입니다.
                throw new RuntimeException("존재하지 않는 회원입니다.");
            }
        }
        else{
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

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
        List<Child> childs = childRepository.findAllByUser(user);
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