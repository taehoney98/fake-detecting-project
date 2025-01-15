package com.aivle.fakedetecting.entity;

import com.aivle.fakedetecting.dto.RequestChangePassword;
import com.aivle.fakedetecting.dto.RequestSignUp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mb_seq")
    private Long seq;
    @Column(name = "mb_email")
    private String email;
    @Column(name = "mb_name")
    private String name;
    @Column(name = "mb_nickName")
    private String nickName;
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
    @Column(name = "mb_address")
    private String address;
//    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
//    private MailAuth mailAuth;
    @OneToMany(mappedBy = "member")
    @JsonBackReference
    private List<Board> boardList;

    public static Member toEntity(RequestSignUp requestSignUp){
        return Member.builder()
                .email(requestSignUp.getEmail())
                .name(requestSignUp.getName())
                .nickName(requestSignUp.getNickName())
                .password(requestSignUp.getPassword())
                .passwordBefore(requestSignUp.getPassword())
                .phone(requestSignUp.getPhone())
                .gender(requestSignUp.getGender())
                .svcAgmt(requestSignUp.isSvcAgmt())
                .infoAgmt(requestSignUp.isInfoAgmt())
                .address(requestSignUp.getAddress())
                .build();
    }

    public void pwdEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        this.passwordBefore = passwordEncoder.encode(this.passwordBefore);
    }

    public void pwdChange(RequestChangePassword requestChangePassword){
        this.password = requestChangePassword.getNewPassword();
        this.passwordBefore = requestChangePassword.getCurrentPassword();
    }
}
