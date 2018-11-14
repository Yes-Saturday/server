package com.ggboy.common.exception;

public class VerifyException extends BaseRuntimeException {
    public VerifyException(String code, String message) {
        super(code, message, "");
    }
}