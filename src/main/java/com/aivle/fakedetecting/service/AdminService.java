package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.config.jwt.JwtUtil;
import com.aivle.fakedetecting.dto.RequestLogin;
import com.aivle.fakedetecting.dto.ResponseLogin;
import com.aivle.fakedetecting.entity.Admin;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
import com.aivle.fakedetecting.error.CustomException;
import com.aivle.fakedetecting.repository.AdminRepository;
import com.aivle.fakedetecting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseLogin adminLogin(RequestLogin requestLogin) {
        Admin findedAdmin = adminRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow(() -> new CustomException("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다."));
        if(!passwordEncoder.matches(requestLogin.getPassword(), findedAdmin.getPassword())){
            throw new CustomException("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다.");
        }
        String token = jwtUtil.createJwt(findedAdmin.getId(), findedAdmin.getEmail(), 24*60*60*1000L, Role.ROLE_ADMIN);

        return ResponseLogin.toResponseAdminLogin(findedAdmin, token);
    }
}
