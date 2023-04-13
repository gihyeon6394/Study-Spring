package com.tob.part4.exception;

/**
 * 사용자가 아이디를 잘못 입력하면 던지는 예외
 * */
public class DuplicatedUserIDException extends RuntimeException {
    public DuplicatedUserIDException(Throwable cause) {
        super(cause);
    }
}
