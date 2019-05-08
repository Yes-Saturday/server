package com.saturday.common.exception;

public class InternalException extends BaseRuntimeException {
    public InternalException(String code, String message) {
        super(code, message, null, null);
    }

    public InternalException(String message) {
        super(null, message, null, null);
    }

    public InternalException(String message, Exception e) {
        super(null, message, null, e);
    }
}
