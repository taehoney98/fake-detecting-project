package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.config.jwt.JwtUtil;
import com.aivle.fakedetecting.dto.*;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.error.CustomException;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.error.MemberNotFound;
import com.aivle.fakedetecting.error.MissingRequiredFieldException;
import com.aivle.fakedetecting.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Transactional
    public Member signUp(RequestSignUp requestSignUp) throws CustomException {
        if(!requestSignUp.isInfoAgmt() || !requestSignUp.isSvcAgmt()){
            throw new CustomException("개인정보 수집 및 서비스 이용약관에 동의하지 않았습니다.");
        }
        Optional<Member> existMember = memberRepository.findByEmail(requestSignUp.getEmail());
        if(existMember.isPresent()){
            throw new CustomException("이미 가입된 이메일 입니다.");
        }
        Member member = Member.toEntity(requestSignUp);
        member.pwdEncode(passwordEncoder);
        return memberRepository.save(member);
    }

    @Transactional
    public ResponseLogin login(RequestLogin requestLogin){
        Member findedMember = memberRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow(() -> new CustomException("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다."));
        if(!passwordEncoder.matches(requestLogin.getPassword(), findedMember.getPassword())){
            throw new CustomException("이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일 입니다.");
        }
        String token = jwtUtil.createJwt(findedMember.getSeq(), findedMember.getEmail(), 24*60*60*1000L);

        return ResponseLogin.toResponseLogin(findedMember, token);
    }

    @Transactional
    public Member changePassword(Long id, RequestChangePassword requestChagePassword) throws CustomException {
        if(!requestChagePassword.getNewPassword().equals(requestChagePassword.getConfirmPassword())){
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }
        Member findedMember = memberRepository.findById(id)
                .orElseThrow(MemberNotFound::new);
        if(!passwordEncoder.matches(requestChagePassword.getCurrentPassword(), findedMember.getPassword())){
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }
        findedMember.pwdChange(requestChagePassword);
        findedMember.pwdEncode(passwordEncoder);
        return findedMember;
    }

    public Member findMember(Long id){
        return memberRepository.findById(id).orElseThrow(MemberNotFound::new);
    }

    public Member updateProfile(Long id, RequestProfile requestProfile){
        Member member = findMember(id);
        member.profileChange(requestProfile);
        return member;
    }
}
