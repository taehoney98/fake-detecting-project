package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLogin {
    private Long id;
    private String email;
    private String token;

    public static ResponseLogin toResponseLogin(Member member, String token){
         return ResponseLogin.builder()
                 .id(member.getSeq())
                 .email(member.getEmail())
                 .token(token)
                 .build();
    }
}
