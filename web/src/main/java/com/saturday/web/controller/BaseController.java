package com.saturday.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
    @Autowired
    protected HttpServletRequest request;

    HttpSession getSession() {
        return getSession(true);
    }

    HttpSession getSession(boolean var1) {
        return request == null ? null : request.getSession(var1);
    }

    <T> T getSessionAttribute(String key) {
        var session = getSession(false);
        return session == null ? null : (T) session.getAttribute(key);
    }

    void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    String getSessionId() {
        return getSession().getId();
    }
}
