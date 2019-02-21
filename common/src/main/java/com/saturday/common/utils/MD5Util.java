package com.saturday.common.utils;

import com.saturday.common.constant.ErrorCodeConstant;
import com.saturday.common.exception.InternalException;

import java.security.MessageDigest;

public class MD5Util {
    public final static String digest(String data) {
        return digest(StringUtil.toBytes(data));
    }

    public final static String digest(byte[] data) {
        try {
            if (data == null)
                return null;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data);
            byte[] temp = messageDigest.digest();
            return StringUtil.toHexString(temp);
        } catch (Exception e) {
            throw new InternalException(ErrorCodeConstant.MD5_DIGEST_ERROR, e.getMessage());
        }
    }
}
