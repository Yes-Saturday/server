package com.saturday.common.exception;

public class AssertException extends BaseRuntimeException {
    public AssertException(String code, String message) {
        super(code, message, "");
    }

    public AssertException(String code, String message, String memo) {
        super(code, message, memo);
    }
}