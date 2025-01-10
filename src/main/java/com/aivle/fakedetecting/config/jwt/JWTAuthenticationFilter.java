package com.aivle.fakedetecting.config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtUtil.extractToken(request);
        if (jwt != null && jwtUtil.isValidToken(jwt) && !jwtUtil.isExpired(jwt)) {
            Claims claims = jwtUtil.extractPayload(jwt);
            String username = claims.get("userName").toString();
            Long userId =  Long.valueOf(claims.get("userId").toString());
            // 인증 객체 설정 (username, authorities 등)
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MemberPrincipal memberPrincipal = new MemberPrincipal(userId, username, null, null);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(memberPrincipal, null, memberPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);  // 필터 체인 이어서 진행
    }
}