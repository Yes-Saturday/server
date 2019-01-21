package com.ggboy.web.interceptor;

import com.ggboy.common.annotation.*;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.VerifyException;
import com.ggboy.common.utils.StringUtil;
import com.ggboy.common.utils.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class VerifyInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod))
            return true;

        Method method = ((HandlerMethod) handler).getMethod();

        for (Parameter parameter : method.getParameters())
            if (parameter.isAnnotationPresent(Verify.class))
                Validator.verify(parameter.getType(), request.getParameterMap());

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}