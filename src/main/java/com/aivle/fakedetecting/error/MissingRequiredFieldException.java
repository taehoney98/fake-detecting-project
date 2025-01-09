package com.aivle.fakedetecting.error;

public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException() {
        super();
    }
    // 메시지를 받는 생성자
    public MissingRequiredFieldException(String message) {
        super(message);
    }

}
