package com.saturday.common.exception;

public class BusinessException extends BaseRuntimeException {
    public BusinessException(String message) {
        super(null, message, null, null);
    }
    public BusinessException(String code, String message) {
        super(code, message, null, null);
    }
}