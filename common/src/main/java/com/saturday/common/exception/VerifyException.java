package com.saturday.common.exception;

public class VerifyException extends BaseRuntimeException {
    public VerifyException(String message) {
        super(null, message, null, null);
    }
}