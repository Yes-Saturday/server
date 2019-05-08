package com.saturday.web.interceptor;

import com.saturday.common.exception.VerifyException;
import com.saturday.common.utils.CacheUtil;
import com.saturday.user.domain.entity.UserBasics;
import com.saturday.web.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {

    private final static Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!"POST".equals(request.getMethod().toUpperCase()))
            return true;

        var uid = request.getHeader("uid");
        var timestamp = request.getHeader("timestamp");
        try {
            long time = System.currentTimeMillis() - Long.parseLong(timestamp);

            if (time > 1000 * 60)
                throw new VerifyException("请求参数非法");

            if (CacheUtil.get(uid + timestamp) != null)
                throw new VerifyException("重复的请求");

            CacheUtil.put(uid + timestamp, "", 60);
        } catch (NumberFormatException e) {
            throw new VerifyException("请求参数非法");
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.clear();
    }
}