package com.ggboy.common.utils;

import com.ggboy.common.constant.BaseConstant;
import com.ggboy.common.constant.SymbolConstant;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public final static String Empty = "";

    public static String toUpperCase(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String toLowerCase(String str) {
        return changeFirstCharacterCase(str, false);
    }

    public static byte[] toBytes(String str) {
        return toBytes(str, BaseConstant.CHARSET_UTF8);
    }

    public static String toString(Object... data) {
        return ArrayUtil.toString(data, null);
    }

    public final static String[] split(String data, String delimiter) {
        return split(data, delimiter, 0);
    }

    public final static String[] split(String data) {
        return split(data, SymbolConstant.COMMA, 0);
    }

    public final static List<String> toList(String data) {
        return toList(data, SymbolConstant.COMMA);
    }

    public final static List<String> toList(String data, String delimiter) {
        String[] strs = StringUtil.split(data, delimiter);

        List<String> list = new ArrayList<>(strs.length);
        for (String item : strs)
            list.add(item);

        return list;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || obj.toString().length() == 0;
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (isEmpty(str))
            return str;

        StringBuilder sb = new StringBuilder(str.length());

        if (capitalize)
            sb.append(Character.toUpperCase(str.charAt(0)));
        else
            sb.append(Character.toLowerCase(str.charAt(0)));

        return sb.append(str.substring(1)).toString();
    }

    public static byte[] toBytes(String str, Charset charset) {
        if (isEmpty(str))
            return new byte[0];

        return str.getBytes(charset);
    }

    public final static String[] split(String data, String delimiter, int index) {
        if (isEmpty(data))
            return null;
        else if (isEmpty(delimiter))
            return new String[]{data};

        return data.split(delimiter, index);
    }

    public final static byte[] hexStringToBytes(String data) {
        if (isEmpty(data))
            return new byte[0];

        if ((data.length() & 1) > 0)
            data = data.substring(0, data.length() - 1) + "0" + data.substring(data.length() - 1);

        byte[] result = new byte[data.length() >> 1];

        char[] ch = data.toCharArray();
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) ((getCharHex(ch[i << 1]) << 4) | getCharHex(ch[(i << 1) + 1]));
        }
        return result;
    }

    private final static int getCharHex(char ch) {
        return ch < 58 ? ch - 48 : ch < 91 ? ch - 55 : ch - 87;
    }

    private final static char getHexChar(int i) {
        return (char) (i < 10 ? i + 48 : i + 55);
    }

    public final static String toHexString(byte[] data) {
        if (data == null || data.length == 0)
            return Empty;

        char[] chars = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            chars[i * 2] = getHexChar((data[i] & 0xff) >>> 4);
            chars[i * 2 + 1] = getHexChar(data[i] & 0xf);
        }
        return new String(chars);
    }
}
