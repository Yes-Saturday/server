package com.ggboy.common.exception;

import com.ggboy.common.utils.StringUtil;

public class BaseException extends Exception {
    private String code;
    private String memo;

    public BaseException(String code, String message, String memo) {
        super(message);
        this.code = code;
        this.memo = memo;
    }

    public BaseException(Exception e) {
        super(e);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDetailMessage() {
        return StringUtil.toString(code, " - ", getMessage(), " : ", memo);
    }
}