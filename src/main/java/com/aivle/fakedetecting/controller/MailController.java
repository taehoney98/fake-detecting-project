package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send-verification-code")
    @ResponseBody
//    TODO: param 수정
    public String sendVerificationCode(@RequestParam String email) {
        String verificationCode = mailService.generateVerificationCode();
        mailService.sendVerificationEmail(email, verificationCode);

        return "이메일로 인증 코드가 전송되었습니다.";
    }
}
