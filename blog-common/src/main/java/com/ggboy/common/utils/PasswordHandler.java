package com.ggboy.common.utils;

public class PasswordHandler {
    public final static String getPwd(byte[] pwd) {
        String pwdHex = StringUtil.toHexString(pwd);
        String salt = MD5Util.digest(pwd);
        return MD5Util.digest(pwdHex + salt);
    }

    public final static String getPwd(String pwd) {
        return getPwd(StringUtil.toBytes(pwd));
    }
}
