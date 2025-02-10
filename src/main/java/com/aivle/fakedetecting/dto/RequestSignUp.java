package com.aivle.fakedetecting.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignUp {
    @Email
    private String email;
    private String name;
    private String nickName;
    private String password;
    private String confirmPassword;
    private String phone;
//    private String gender;
    private boolean svcAgmt;
    private boolean infoAgmt;
//    private String address;
    private Integer receivedCode;
}
