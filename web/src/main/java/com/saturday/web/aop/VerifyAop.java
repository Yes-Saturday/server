package com.saturday.web.aop;

import com.saturday.common.annotation.Verify;
import com.saturday.common.exception.InternalException;
import com.saturday.common.utils.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class VerifyAop {
    @Before("execution(* com.saturday.web.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        var args = joinPoint.getArgs();
        if (args == null || args.length == 0)
            return;

        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            var parameters = method.getParameters();
            for (var i = 0; i < parameters.length; ++i)
                if (parameters[i].isAnnotationPresent(Verify.class))
                    Validator.verify(args[i]);
        } catch (Exception e) {
            throw new InternalException("参数校验发生未知异常", e);
        }
    }
}
