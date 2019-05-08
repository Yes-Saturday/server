package com.saturday.common.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrontResponse {
    private String code;
    private String message;
    private Object data;

    private FrontResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public final static FrontResponse fail(String message) {
        return fail(null, message);
    }

    public final static FrontResponse fail(String code, String message) {
        return new FrontResponse(code, message);
    }

    public final static FrontResponse success() {
        return new FrontResponse(null, null);
    }

    public final static FrontResponse success(Object data) {
        return new FrontResponse(null, null){{setData(data);}};
    }
}
