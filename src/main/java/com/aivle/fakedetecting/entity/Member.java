package com.aivle.fakedetecting.entity;

import com.aivle.fakedetecting.dto.RequestSignUp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity{
    @Id
    @Column(name = "mb_seq")
    private Long seq;
    @Column(name = "mb_email")
    private String email;
    @Column(name = "mb_pwd")
    private String password;
    @Column(name = "mb_pwd_before")
    private String passwordBefore;
    @Column(name = "mb_phone")
    private String phone;
    @Column(name = "mb_gender")
    private String gender;
    @Column(name = "mb_svc_use_pcy_agmt")
    private boolean svcAgmt;
    @Column(name = "mb_svc_info_proc_agmt")
    private boolean infoAgmt;

    public static Member toEntity(RequestSignUp requestSignUp){
        return Member.builder()
                .email(requestSignUp.getEmail())
                .password(requestSignUp.getPassword())
                .passwordBefore(requestSignUp.getPassword())
                .phone(requestSignUp.getPhone())
                .gender(requestSignUp.getGender())
                .svcAgmt(requestSignUp.isSvcAgmt())
                .infoAgmt(requestSignUp.isInfoAgmt())
                .build();
    }
}
