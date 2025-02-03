package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.error.CustomException;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ApiResult<Boolean> handleException(Exception e){
        return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<Boolean> handleIllegalArgumentException(IllegalArgumentException e){
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), "잘못된 요청");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ApiResult<Boolean> handleCustomException(CustomException e){
        return ApiResult.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.InternalServerError.class)
    public ApiResult<Boolean> handleFeignInternalServerError(FeignException.InternalServerError e) {
        return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "분석 실패");
    }
}
