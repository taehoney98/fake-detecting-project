package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send-verification-code")
    @ResponseBody
    public ApiResult<String> sendVerificationCode(@RequestBody Map<String, String> map) throws EmailAlreadyExistsException {
        String verificationCode = mailService.generateVerificationCode();
        mailService.sendVerificationEmail(map.get("email"), verificationCode);

        return ApiResult.success(verificationCode, "이메일로 인증 코드가 전송되었습니다.");
    }
}
