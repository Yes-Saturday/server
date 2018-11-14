package com.ggboy.web.interceptor;

import com.ggboy.common.annotation.*;
import com.ggboy.common.constant.ErrorCodeConstant;
import com.ggboy.common.exception.VerifyException;
import com.ggboy.common.utils.StringUtil;
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

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        for (Parameter parameter : method.getParameters())
            if (parameter.isAnnotationPresent(Verify.class)) {
                Class<?> clazz = parameter.getType();
                for (Field field : clazz.getDeclaredFields()) {
                    Name nameAnnotation = field.getAnnotation(Name.class);
                    String name = nameAnnotation == null ? field.getName() : nameAnnotation.value();
                    String[] values = request.getParameterValues(field.getName());

                    if (values != null && values.length > 1)
                        isTrue(false, StringUtil.toString("{", name, "}参数非法"));

                    String value = values == null ? null : values.length > 0 ? values[0] : null;

                    if (StringUtil.isEmpty(value))
                        if (field.isAnnotationPresent(NotNull.class))
                            isTrue(false, StringUtil.toString("{", name, "}不能为空"));
                        else
                            continue;

                    if (field.getType().isEnum()) {
                        boolean flag = false;
                        for (Object item : field.getType().getEnumConstants())
                            if (item.toString().equals(value)) {
                                flag = true;
                                break;
                            }
                        isTrue(flag, StringUtil.toString("{", name, "}参数非法"));
                    }

                    Length length = field.getAnnotation(Length.class);
                    if (length != null)
                        isTrue(value.length() == length.value(), StringUtil.toString("{", name, "}字段长度不正确"));

                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    if (maxLength != null)
                        isFalse(value.length() > maxLength.value(), StringUtil.toString("{", name, "}字段长度超长"));

                    MinLength minLength = field.getAnnotation(MinLength.class);
                    if (minLength != null)
                        isFalse(value.length() < minLength.value(), StringUtil.toString("{", name, "}字段长度过短"));
                }
            }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    private final void isTrue(boolean bool, String message) {
        isFalse(!bool, message);
    }

    private final void isFalse(boolean bool, String message) {
        if (bool)
            throw new VerifyException(ErrorCodeConstant.PARAMETER_ERROR, message);
    }
}