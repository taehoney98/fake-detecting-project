package com.aivle.fakedetecting.config.jwt;

import com.aivle.fakedetecting.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter
@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberDetailService memberDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtUtil.extractToken(request);
        try{
            if(jwt != null && jwtUtil.isValidToken(jwt) && !jwtUtil.isExpired(jwt)) {
                Claims claims = jwtUtil.extractPayload(jwt);
                String username = claims.get("userName").toString();
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    MemberPrincipal memberPrincipal = (MemberPrincipal) memberDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(memberPrincipal, null, memberPrincipal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (ExpiredJwtException e){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
//        if (jwt != null && jwtUtil.isValidToken(jwt) && !jwtUtil.isExpired(jwt)) {
//            Claims claims = jwtUtil.extractPayload(jwt);
//            String username = claims.get("userName").toString();
////            Long userId =  Long.valueOf(claims.get("userId").toString());
////            String role = claims.get("role").toString();
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                // GrantedAuthority를 사용해 권한 리스트 설정
////                List<GrantedAuthority> authorities;
////                if ("ROLE_ADMIN".equals(role)) {
////                    authorities = List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getRole())); // admin 권한 설정
////                } else {
////                    authorities = List.of(new SimpleGrantedAuthority(Role.ROLE_USER.getRole())); // user 권한 설정
////                }
//
//                // MemberPrincipal을 사용해 사용자 정보와 권한을 설정
////                MemberPrincipal memberPrincipal = new MemberPrincipal(userId, username, null, authorities);
//                MemberPrincipal memberPrincipal = (MemberPrincipal) memberDetailService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(memberPrincipal, null, memberPrincipal.getAuthorities());
//
//                // 인증 객체를 SecurityContext에 설정
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }

        filterChain.doFilter(request, response);  // 필터 체인 이어서 진행
    }
}