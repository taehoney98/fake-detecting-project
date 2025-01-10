package com.aivle.fakedetecting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestChangePassword {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
