package com.example.ai_jeju.handler;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.RefreshToken;
import com.example.ai_jeju.domain.User;
import com.example.ai_jeju.dto.ChildRequest;
import com.example.ai_jeju.dto.SignUpRequest;
import com.example.ai_jeju.generator.NickNameGenerator;
import com.example.ai_jeju.jwt.TokenProvider;
import com.example.ai_jeju.repository.ChildRepository;
import com.example.ai_jeju.repository.RefreshTokenRepository;
import com.example.ai_jeju.repository.UserRepository;
import com.example.ai_jeju.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.example.ai_jeju.util.CookieUtil.addCookie;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SignUpHandler{
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

    HttpServletResponse response;
    RefreshTokenRepository refreshTokenRepository;
    CookieUtil cookieUtil;

    @Autowired
    private ChildRepository childRepository;
    private UserRepository userRepository;

//    public String successHadler(HttpServletRequest request,
//                                HttpServletResponse response, SignUpRequest signUpRequest, TokenProvider tokenProvider) throws IOException {
//
//        String nick = signUpRequest.getNickname();
//        if(nick==null){
//            nick = new NickNameGenerator().getNickname();
//        }
//        // Save new user using builder pattern
//        User newUser = User.builder()
//                .name(signUpRequest.getName())
//                .nickname(nick)
//                .provider(signUpRequest.getProvider())
//                .email(signUpRequest.getEmail())
//                .snsprofile(signUpRequest.getProfile())
//                .provider(signUpRequest.getProvider())
//                .phoneNum(signUpRequest.getPhoneNum())
//                .build();
//
//
//        String accessToken = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
//        /*-------------------------------------------*/
//        //동반아동
//        List<ChildRequest> childList = signUpRequest.getChild();
//        for(int i=0; i<childList.size(); i++){
//            Child child = Child.builder()
//                    .userId(newUser.getId())
//                    .childName(childList.get(i).getChildName())
//                    .birthDate(childList.get(i).getBirthDate())
//                    .gender(childList.get(i).getGender())
//                    .build();
//            childRepository.save(child);
//        }
//
//        userRepository.save(newUser);
//        String refresh_token = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
//        /*
//        RefreshToken refreshToken = RefreshToken.builder()
//                        .refresh_token(refresh_token)
//                                .userId(newUser.getId()).build();*/
//
//        /*
//        refreshTokenRepository.save(refreshToken);
//        userRepository.save(newUser);
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refresh_token, cookieMaxAge);*/
//
//        saveRefreshToken(newUser.getId(), refresh_token);
//        addRefreshTokenToCookie(request,response,refresh_token);
//        return  accessToken;
//    }

    //생성된 리프레시 토큰을 전달받아 데이터베이스 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    //생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request,
                                         HttpServletResponse response, String accessToken) {
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken, cookieMaxAge);

    }







}
