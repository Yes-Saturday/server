package com.saturday.common.exception;

import com.saturday.common.utils.StringUtil;

public class BaseRuntimeException extends RuntimeException {
    private String code;
    private String memo;

    public BaseRuntimeException(String code, String message, String memo, Throwable e) {
        super(message, e);
        this.code = code;
        this.memo = memo;
    }

    public String getCode() {
        return code;
    }

    public String getMemo() {
        return memo;
    }

    public String getDetailMessage() {
        return StringUtil.toString(code, " - ", getMessage(), " : ", memo);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}