package com.saturday.common.context;

import com.saturday.common.utils.UuidUtil;

public class UuidContext {
    private final static ThreadLocal<String> uuid = new ThreadLocal<>();

    public final static String get() {
        return uuid.get();
    }

    public final static void init() {
        uuid.set(UuidUtil.getUuid());
    }
}