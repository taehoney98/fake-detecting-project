package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.CustomAuthenticationToken;
import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.*;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.error.MissingRequiredFieldException;
import com.aivle.fakedetecting.service.AdminService;
import com.aivle.fakedetecting.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AdminService adminService;
    @PostMapping("/signup")
    @ResponseBody
    public ApiResult<Boolean> signUp(@RequestBody RequestSignUp requestSignUp) throws MissingRequiredFieldException, EmailAlreadyExistsException {
        memberService.signUp(requestSignUp);
        return ApiResult.success(true, "회원가입 성공");
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResult<ResponseLogin> login(@RequestBody RequestLogin requestLogin, HttpServletResponse response){
        //email @이전에 admin이있는지 확인
        if(requestLogin.getEmail().toLowerCase().startsWith("admin")){
            ResponseLogin responseLogin = adminService.adminLogin(requestLogin);
            CustomAuthenticationToken customAuthenticationToken =
                    new CustomAuthenticationToken(responseLogin.getId()
                            , responseLogin.getEmail(), responseLogin.getToken(), Role.ROLE_ADMIN);
            SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
            response.setHeader(HttpHeaders.AUTHORIZATION, responseLogin.getToken());
            return ApiResult.success(responseLogin, "로그인 성공");
        }
        ResponseLogin responseLogin = memberService.login(requestLogin);
        CustomAuthenticationToken customAuthenticationToken =
                new CustomAuthenticationToken(responseLogin.getId()
                        , responseLogin.getEmail(), responseLogin.getToken(), Role.ROLE_USER);
        SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, responseLogin.getToken());

        return ApiResult.success(responseLogin, "로그인 성공");
    }

    @PutMapping("/password")
    @ResponseBody
    public ApiResult<Boolean> changePassword(@RequestBody RequestChangePassword requestChangePassword
            , @AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        Member member = memberService.changePassword(memberPrincipal.getUserId(), requestChangePassword);
        return ApiResult.success(true, "비밀번호 변경 성공");
    }
    @PutMapping("/profile")
    @ResponseBody
    public ApiResult<Boolean> changeProfile(@RequestBody RequestProfile requestProfile
            , @AuthenticationPrincipal MemberPrincipal memberPrincipal){
        memberService.updateProfile(memberPrincipal.getUserId(), requestProfile);
        return ApiResult.success(true, "프로필 수정 완료");
    }

    @GetMapping("/profile")
    @ResponseBody
    public ApiResult<ResponseProfile> getProfile(@AuthenticationPrincipal MemberPrincipal memberPrincipal){
        Member member = memberService.findMember(memberPrincipal.getUserId());
        ResponseProfile responseProfile = ResponseProfile.toResponseProfile(member);
        return ApiResult.success(responseProfile, "프로필 조회 성공");
    }

    @PostMapping("/member/email")
    @ResponseBody
    public ApiResult<String> getEmail(@RequestBody RequestFindMember requestFindMember){
        Member member = memberService.findMemberByNameAndPhone(requestFindMember);
        return ApiResult.success(member.getEmail(), "성공");
    }

    @PostMapping("/member/exist")
    @ResponseBody
    public ApiResult<Boolean> getMemberExist(@RequestBody RequestFindMember requestFindMember){
        Member member = memberService.findMemberByNameAndEmailAndPhone(requestFindMember);
        return ApiResult.success(true, "성공");
    }

    @PutMapping("/member/password")
    @ResponseBody
    public ApiResult<Boolean> changePassword(@RequestBody RequestNewPassword requestNewPassword){
        Member member = memberService.setNewPassword(requestNewPassword);
        return ApiResult.success(true, "성공");
    }
}
