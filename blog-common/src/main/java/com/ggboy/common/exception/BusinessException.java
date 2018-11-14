package com.ggboy.common.exception;

public class BusinessException extends BaseRuntimeException {
    public BusinessException(String code, String message) {
        super(code, message, "");
    }

    public BusinessException(String code, String message, String memo) {
        super(code, message, memo);
    }
}
