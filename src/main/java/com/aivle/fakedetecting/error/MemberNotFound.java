package com.aivle.fakedetecting.error;

public class MemberNotFound extends RuntimeException {
    public MemberNotFound(){ super("member not found");}

    public MemberNotFound(String message) {
        super(message);
    }
}
