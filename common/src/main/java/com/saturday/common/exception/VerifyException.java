package com.saturday.common.exception;

public class VerifyException extends BaseRuntimeException {
    public VerifyException(String code, String message) {
        super(code, message, "");
    }
}