package com.saturday.common.exception;

public class _404Exception extends BaseRuntimeException {

    public final static _404Exception DEFAULT_404 = new _404Exception();

    private _404Exception() {
        super(null, null, null, null);
    }
}
