package com.ggboy.common.domain;

public class FrontEndResponse {
    private String code;
    private String message;
    private Object data;

    public FrontEndResponse(Object data) {
        this.code = "OK";
        this.message = "";
        this.data = data;
    }

    public FrontEndResponse() {
        this("");
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
}
