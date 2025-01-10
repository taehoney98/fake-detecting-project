package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.dto.RequestSignUp;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.error.MissingRequiredFieldException;
import com.aivle.fakedetecting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
        return memberRepository.save(member);
    }
}
