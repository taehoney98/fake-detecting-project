package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.MailAuth;
import com.aivle.fakedetecting.error.EmailAlreadyExistsException;
import com.aivle.fakedetecting.repository.MailRepository;
import com.aivle.fakedetecting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final MailRepository mailRepository;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));  // 0-9까지의 숫자
        }
        return code.toString();
    }
    public void sendVerificationEmail(String toEmail, String verificationCode) throws EmailAlreadyExistsException {
        if(!memberRepository.findByEmail(toEmail).isEmpty()) {
            throw new EmailAlreadyExistsException();
        }

        String subject = "이메일 인증 코드";
        String body = "안녕하세요! 이메일 인증 코드입니다.\n" + "인증 코드: " + verificationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);


    }

    public MailAuth saveMailAuth(MailAuth mailAuth) {
        return mailRepository.save(mailAuth);
    }
}
