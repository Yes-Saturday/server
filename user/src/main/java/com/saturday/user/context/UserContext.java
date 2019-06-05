package com.saturday.user.context;

import com.saturday.user.domain.entity.UserBasics;

public class UserContext {
    private final static ThreadLocal<UserBasics> userBasicsLocal = new ThreadLocal<>();

    public final static ThreadLocal<UserBasics> getUserContext() {
        return userBasicsLocal;
    }

    public final static void clear() {
        userBasicsLocal.remove();
    }
}