package com.ggboy.common.exception;

public class CommonUtilException extends BaseException {
    public CommonUtilException(String code, String message, String memo) {
        super(code, message, memo);
    }

    public CommonUtilException(String code, String message) {
        super(code, message, "");
    }

    public CommonUtilException(Exception e) {
        super(e);
    }
}
