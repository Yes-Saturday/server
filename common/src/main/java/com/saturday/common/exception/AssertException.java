package com.saturday.common.exception;

public class AssertException extends BaseRuntimeException {
    public AssertException(String message) {
        super(null, message, null, null);
    }
}