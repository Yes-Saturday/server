package com.ggboy.common.utils;

import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.AssertException;

public class Assert {
    public final static void isNull(Object obj, String message) {
        if (obj != null)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void isNotNull(Object obj, String message) {
        if (obj == null)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void isNotEmpty(String str, String message) {
        if (StringUtil.isEmpty(str))
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void isEmpty(Object obj, String message) {
        if (!StringUtil.isEmpty(obj))
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void isTrue(boolean bol, String message) {
        if (!bol)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void isFalse(boolean bol, String message) {
        if (bol)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void maxLength(Object obj, int maxLength, String message) {
        if (obj != null && obj.toString().length() > maxLength)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message, obj.toString());
    }

    public final static void minLength(String str, int minLength, String message) {
        if (str != null && str.length() < minLength)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }

    public final static void length(Object obj, int length, String message) {
        if (obj != null && obj.toString().length() != length)
            throw new AssertException(ErrorCodeConstant.PARAMETER_ERROR, message, obj.toString());
    }
}