package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.CustomAuthenticationToken;
import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.*;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.error.MissingRequiredFieldException;
import com.aivle.fakedetecting.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestBody RequestSignUp requestSignUp) throws MissingRequiredFieldException, EmailAlreadyExistsException {
        memberService.signUp(requestSignUp);
        return requestSignUp.getEmail();
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody RequestLogin requestLogin, HttpServletResponse response){
        ResponseLogin responseLogin = memberService.login(requestLogin);
        CustomAuthenticationToken customAuthenticationToken =
                new CustomAuthenticationToken(responseLogin.getId()
                        , responseLogin.getEmail(), responseLogin.getToken());
        SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, responseLogin.getToken());

        return responseLogin.getEmail();
    }

    @PutMapping("/password")
    @ResponseBody
    public Member changePassword(@RequestBody RequestChangePassword requestChangePassword
            , @AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        Member member = memberService.changePassword(memberPrincipal.getUserId(), requestChangePassword);
        return member;
    }
    @PutMapping("/profile")
    @ResponseBody
    public Member changeProfile(@RequestBody RequestProfile requestProfile
            , @AuthenticationPrincipal MemberPrincipal memberPrincipal){
        return memberService.updateProfile(memberPrincipal.getUserId(), requestProfile);
    }
}
