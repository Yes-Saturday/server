package com.saturday.common.utils;

public class PasswordHandler {
    public final static String getPwd(byte[] pwd) {
        return getPwd(pwd, MD5Util.digest(pwd));
    }

    public final static String getPwd(String pwd) {
        return getPwd(StringUtil.toBytes(pwd));
    }

    public final static String getPwd(String pwd, String salt) {
        return getPwd(StringUtil.toBytes(pwd), salt);
    }

    public final static String getPwd(byte[] pwd, String salt) {
        String pwdHex = StringUtil.toHexString(pwd);
        return MD5Util.digest(pwdHex + salt);
    }
}
