package com.saturday.common.domain;

public class FrontEndResponse {
    private String code;
    private String message;
    private Object data;

    private FrontEndResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public final static FrontEndResponse success(Object data) {
        return new FrontEndResponse("OK", null, data);
    }

    public final static FrontEndResponse success() {
        return success(null);
    }

    public final static FrontEndResponse fail(String code, String message) {
        return new FrontEndResponse(code, message, null);
    }
}
