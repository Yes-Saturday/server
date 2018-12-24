package com.ggboy.common.convert;

public class SequenceIdConvert {
    public final static String convert(String flag, Long number, Integer length) {
        if (flag == null)
            flag = "";

        if (length == null || length < flag.length())
            return flag + number;

        String numStr = String.format("%0" + (length - flag.length()) + "d", number);
        return flag + numStr;
    }

    public final static String convert(String flag, Long number) {
        return convert(flag, number, null);
    }

    public final static String convert(Long number) {
        return convert(null, number, null);
    }
}