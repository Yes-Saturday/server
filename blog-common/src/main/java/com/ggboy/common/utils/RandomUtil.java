package com.ggboy.common.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    private final static Random random = new Random();

    private final static char[] NUMBER_CHARS = "0123456789".toCharArray();

    public final static String produceConfirmationCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(NUMBER_CHARS[random.nextInt(10)]);
        return sb.toString();
    }

    public final static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
