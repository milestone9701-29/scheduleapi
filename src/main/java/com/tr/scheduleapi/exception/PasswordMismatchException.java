package com.tr.scheduleapi.exception; // 예외


// @ResponseStatus(HttpStatus.FORBIDDEN)
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password does not match.");
    }
}
