package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseProfile {
    private String email;
    private String name;
    private String nickName;
    private String phone;

    public static ResponseProfile toResponseProfile(Member member) {
        return ResponseProfile.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickName())
                .phone(member.getPhone())
                .build();
    }
}
