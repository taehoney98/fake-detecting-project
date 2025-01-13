package com.aivle.fakedetecting.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Slf4j
@Component
public class JwtUtil {
    public final SecretKey key;

    public JwtUtil(@Value("${service.jwt.secret-key}") String secretKey){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }
//    public final SecretKey key = Jwts.SIG.HS256.key().build();

    public String createJwt(Long userId, String userName, Long expiredMs){
        return Jwts.builder()
                .claim("userName", userName)
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key)
                .compact();
    }
    public boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public Claims extractPayload(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        log.error("token 에러");
        return null;
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true; // 토큰이 유효하면 true
        } catch (Exception e) {
            log.error("token 만료");
            return false; // 토큰이 무효하면 false
        }
    }
}
