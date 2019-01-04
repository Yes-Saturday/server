package com.ggboy.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseInterceptor implements HandlerInterceptor {

    private final static ThreadLocal<Long> timer = new ThreadLocal<>();
    private final static Logger log = LoggerFactory.getLogger(BaseInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        timer.set(System.currentTimeMillis());
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long time = System.currentTimeMillis();
        log.info("Controller 处理时间 : {}(ms)", time - timer.get());
        timer.set(time);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("渲染、以及其他拦截器处理时间 : {}(ms)", System.currentTimeMillis() - timer.get());
    }
}
