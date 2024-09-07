package com.example.ai_jeju.jwt;


import com.example.ai_jeju.domain.User;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import io.jsonwebtoken.*;



@RequiredArgsConstructor
@Slf4j
@Service
public class TokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 안전한 키 생성

    // 토큰 생성하기
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("issuer")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("nickname", user.getNickname())
                .claim("profileImg", user.getProfileImg())
                .signWith(key, SignatureAlgorithm.HS512) // 동일한 키와 알고리즘 사용
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        log.info("validToken method called with token: {}", token);
        try {
            Jwts.parser()
                    .setSigningKey(key) // 동일한 키와 알고리즘 사용
                    .parseClaimsJws(token)
                    .getBody();
            log.info("JWT token is valid");
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during token validation", e);
        }
        return false;
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    public String extractNickname(String token) {
        Claims claims = getClaims(token);
        return claims.get("nickname", String.class);
    }

    public String extractProfileImg(String token) {
        Claims claims = getClaims(token);
        return claims.get("profileImg", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
