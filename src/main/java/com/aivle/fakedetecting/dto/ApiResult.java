package com.aivle.fakedetecting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult <T>{
    private int status;
    private T data;
    private String message;

    // 성공 응답을 위한 메서드
    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<>(200, data, message);
    }

    // 실패 응답을 위한 메서드 (에러 상세 내용 추가)
    public static <T> ApiResult<T> error(int status, String message) {
        return new ApiResult<>(status, null, message);
    }
}
