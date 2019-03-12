package com.saturday.common.utils;

import com.saturday.common.exception.AssertException;

public class Assert {
    public final static void isNull(Object obj, String message) {
        isFalse(obj != null, message);
    }

    public final static void isNotNull(Object obj, String message) {
        isFalse(obj == null, message);
    }

    public final static void isNotEmpty(Object obj, String message) {
        isFalse(StringUtil.isEmpty(obj), message);
    }

    public final static void isEmpty(Object obj, String message) {
        isTrue(StringUtil.isEmpty(obj), message);
    }

    public final static void isTrue(boolean bol, String message) {
        isFalse(!bol, message);
    }

    public final static void isFalse(boolean bol, String message) {
        if (bol)
            throw new AssertException(message);
    }

    public final static void maxLength(Object obj, int maxLength, String message) {
        if (obj != null && obj.toString().length() > maxLength)
            throw new AssertException(message);
    }

    public final static void minLength(String str, int minLength, String message) {
        if (str != null && str.length() < minLength)
            throw new AssertException(message);
    }

    public final static void length(Object obj, int length, String message) {
        if (obj != null && obj.toString().length() != length)
            throw new AssertException(message);
    }
}