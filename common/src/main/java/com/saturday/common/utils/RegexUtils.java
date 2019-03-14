package com.saturday.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 正则：手机号（简单）
     */
    public static final String MOBILE = "^[1]\\d{10}$";
    /**
     * 正则：身份证号码15位
     */
    public static final String ID_CARD_FIFTEEN     = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    public static final String ID_CARD_EIGHTEEN     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 正则：邮箱
     */
    public static final String EMAIL         = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final String DATE          = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 正则：yyyy-MM格式的日期校验
     */
    public static final String DATEMONTH     ="^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])|(?:0[13-9]|1[0-2])|(?:0[13578]|1[02]))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02)$";
    /**
     * 正则：IP地址
     */
    public static final String IP            = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }
}
