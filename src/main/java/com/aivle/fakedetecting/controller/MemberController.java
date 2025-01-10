package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.dto.RequestSignUp;
import com.aivle.fakedetecting.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestBody RequestSignUp requestSignUp){



        return requestSignUp.getEmail();
    }
}
