package com.saturday.web.interceptor;

import com.saturday.common.annotation.Verify;
import com.saturday.common.exception.CommonUtilException;
import com.saturday.common.utils.IoUtil;
import com.saturday.common.utils.Validator;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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