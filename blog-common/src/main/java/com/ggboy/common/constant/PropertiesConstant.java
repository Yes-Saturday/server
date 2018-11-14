package com.ggboy.common.constant;

import com.ggboy.common.exception.InternalException;

public class PropertiesConstant {
    private static boolean init = false;
    private static Integer DEFAULT_SMS_PAGE_SIZE;

    public static void init(Integer pageSize) {
        if (init)
            throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "配置文件已被初始化");
        init = true;
        DEFAULT_SMS_PAGE_SIZE = pageSize;
    }

    public static Integer getDefaultSmsPageSize() {
        return DEFAULT_SMS_PAGE_SIZE;
    }
}
