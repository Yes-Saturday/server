package com.ggboy.common.exception;

public class InternalException extends BaseRuntimeException {

    public InternalException(String code, String message) {
        super(code, message, "");
    }

    public InternalException(String code, String message, String memo) {
        super(code, message, memo);
    }

    public InternalException(Exception e) {
        super(e);
    }
}
