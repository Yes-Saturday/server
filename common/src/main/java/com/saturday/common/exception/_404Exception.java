package com.saturday.common.exception;

public class _404Exception extends BaseRuntimeException {

    public final static _404Exception default_404 = new _404Exception();

    public _404Exception() {
        super(null);
    }
}
