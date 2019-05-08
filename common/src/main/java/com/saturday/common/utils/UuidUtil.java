package com.saturday.common.utils;

import java.util.UUID;

public class UuidUtil {
    public final static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
