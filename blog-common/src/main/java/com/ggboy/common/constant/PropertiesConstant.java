package com.ggboy.common.constant;

import com.ggboy.common.exception.InternalException;

public class PropertiesConstant {
    private static boolean init = false;
    private static Integer DEFAULT_PAGE_SIZE;
    private static Integer DEFAULT_BLOG_LIST_PAGE_SIZE;
    private static Integer DEFAULT_BLOG_LIST_SHOW_PAGE_BUTTON_SIZE;
    private static String FRIEND_LINK_TYPE;

    public static void init(Integer pageSize, Integer blogListPageSize, Integer blogListShowPageButtonSize, String friendLinkType) {
        if (init)
            throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "配置文件已被初始化");
        init = true;
        DEFAULT_PAGE_SIZE = pageSize;
        DEFAULT_BLOG_LIST_PAGE_SIZE = blogListPageSize;
        DEFAULT_BLOG_LIST_SHOW_PAGE_BUTTON_SIZE = blogListShowPageButtonSize;
        FRIEND_LINK_TYPE = friendLinkType;
    }

    public static Integer getDefaultPageSize(){
        return DEFAULT_PAGE_SIZE;
    }

    public static Integer getDefaultBlogListPageSize() {
        return DEFAULT_BLOG_LIST_PAGE_SIZE;
    }

    public static Integer getDefaultBlogListShowPageButtonSize() {
        return DEFAULT_BLOG_LIST_SHOW_PAGE_BUTTON_SIZE;
    }

    public static String getFriendLinkType() {
        return FRIEND_LINK_TYPE;
    }
}
