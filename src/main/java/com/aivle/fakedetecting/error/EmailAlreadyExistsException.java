package com.aivle.fakedetecting.error;

public class EmailAlreadyExistsException extends Exception{

    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
