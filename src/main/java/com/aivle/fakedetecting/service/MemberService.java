package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.config.jwt.JwtUtil;
import com.aivle.fakedetecting.dto.RequestChangePassword;
import com.aivle.fakedetecting.dto.RequestLogin;
import com.aivle.fakedetecting.dto.RequestSignUp;
import com.aivle.fakedetecting.dto.ResponseLogin;
import com.aivle.fakedetecting.entity.Member;
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
    public Member signUp(RequestSignUp requestSignUp) throws MissingRequiredFieldException, EmailAlreadyExistsException {
        if(!requestSignUp.isInfoAgmt() || !requestSignUp.isSvcAgmt()){
            throw new MissingRequiredFieldException("Agmt is false");
        }
        Optional<Member> existMember = memberRepository.findByEmail(requestSignUp.getEmail());
        if(existMember.isPresent()){
            throw new EmailAlreadyExistsException("The meail " + requestSignUp.getEmail() +
                    " is already registerd.");
        }
        Member member = Member.toEntity(requestSignUp);
        member.pwdEncode(passwordEncoder);
        return memberRepository.save(member);
    }

    @Transactional
    public ResponseLogin login(RequestLogin requestLogin){
        Member findedMember = memberRepository.findByEmail(requestLogin.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
        if(!passwordEncoder.matches(requestLogin.getPassword(), findedMember.getPassword())){
            throw new BadCredentialsException("Invalid username or password.");
        }
        String token = jwtUtil.createJwt(findedMember.getSeq(), findedMember.getEmail(), 24*60*1000L);

        return ResponseLogin.toResponseLogin(findedMember, token);
    }

    @Transactional
    public Member changePassword(Long id, RequestChangePassword requestChagePassword) throws Exception {
        if(!requestChagePassword.getNewPassword().equals(requestChagePassword.getConfirmPassword())){
            throw new Exception("password not match");
        }
        Member findedMember = memberRepository.findById(id)
                .orElseThrow(MemberNotFound::new);
        if(!passwordEncoder.matches(requestChagePassword.getCurrentPassword(), findedMember.getPassword())){
            throw new Exception("password not match");
        }
        findedMember.pwdChange(requestChagePassword);
        findedMember.pwdEncode(passwordEncoder);
        return findedMember;
    }

    public Member findMember(Long id){
        return memberRepository.findById(id).orElseThrow(MemberNotFound::new);
    }
}
