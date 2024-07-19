package com.example.ai_jeju.handler;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.RefreshToken;
import com.example.ai_jeju.domain.User;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.example.ai_jeju.util.CookieUtil.addCookie;


@AllArgsConstructor
public class SignUpHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

    HttpServletResponse response;


    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    TokenProvider tokenProvider;
    CookieUtil cookieUtil;
    ChildRepository childRepository;

    public String successHadler(SignUpRequest signUpRequest) throws IOException {

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
                .profile(signUpRequest.getProfile())
                .provider(signUpRequest.getProvider())
                .build();


        String accessToken = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
        /*-------------------------------------------*/
        //동반아동
        List<Child> childList = signUpRequest.getChild();
        for(int i=0; i<childList.size(); i++){
            Child child = Child.builder()
                    .userId(newUser.getId())
                    .childName(childList.get(i).getChildName())
                    .birthDate(childList.get(i).getBirthDate())
                    .gender(childList.get(i).getGender())
                    .build();
            childRepository.save(child);
        }

        userRepository.save(newUser);

        String refresh_token = tokenProvider.generateToken(newUser, REFRESH_TOKEN_DURATION);
        RefreshToken refreshToken = RefreshToken.builder()
                        .refresh_token(refresh_token)
                                .userId(newUser.getId()).build();

        refreshTokenRepository.save(refreshToken);

        userRepository.save(newUser);

        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refresh_token, cookieMaxAge);

        return  accessToken;
    }



}
