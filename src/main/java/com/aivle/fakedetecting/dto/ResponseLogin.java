package com.aivle.fakedetecting.dto;

import com.aivle.fakedetecting.entity.Admin;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLogin {
    private Long id;
    private String email;
    private String phone;
    private String token;
    private String name;
    private Role role;

    public static ResponseLogin toResponseLogin(Member member, String token){
         return ResponseLogin.builder()
                 .id(member.getSeq())
                 .email(member.getEmail())
                 .name(member.getName())
                 .phone(member.getPhone())
                 .token(token)
                 .role(Role.ROLE_USER)
                 .build();
    }
    public static ResponseLogin toResponseAdminLogin(Admin admin, String token){
        return ResponseLogin.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .token(token)
                .role(Role.ROLE_ADMIN)
                .build();
    }

}
