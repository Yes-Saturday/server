package com.ggboy.web.interceptor;

import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PurviewInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object memberInfo = request.getSession().getAttribute("memberInfo");
        if (memberInfo == null)
            throw new BusinessException(ErrorCodeConstant.NOT_LOGIN, "用户未登录");
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
