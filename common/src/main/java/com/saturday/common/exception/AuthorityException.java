package com.saturday.common.exception;

public class AuthorityException extends BaseRuntimeException {
    public AuthorityException(ResponseCode code) {
        super(code.code, null, null, null);
    }

    public enum ResponseCode {
        not_login("999"),
        restrict_access("991");

        private String code;

        ResponseCode(String code) {
            this.code = code;
        }
    }
}