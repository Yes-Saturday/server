package com.saturday.web.aop;

import com.saturday.auth.service.AuthService;
import com.saturday.common.annotation.Auth;
import com.saturday.common.annotation.Verify;
import com.saturday.common.context.UuidContext;
import com.saturday.common.exception.AssertException;
import com.saturday.common.exception.AuthorityException;
import com.saturday.common.exception.VerifyException;
import com.saturday.common.utils.JsonUtil;
import com.saturday.common.utils.Validator;
import com.saturday.user.domain.entity.UserBasics;
import com.saturday.web.context.RequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class ControllerAspect {

    private final static Logger log = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    private AuthService authService;

    @Around("execution(* com.saturday.web.controller..*Controller.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        long timer = System.currentTimeMillis();
        UuidContext.init();

        var args = joinPoint.getArgs();
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        log.info("Method : [{}], Params : {}, UUID : {}", method.getName(), JsonUtil.toJson(args), UuidContext.get());

        verifyAuth(method);
        verifyData(method, args);

        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.info("Error! Exception : {}, UUID : {}", throwable.getClass().getSimpleName() +"@" + throwable.getMessage(), UuidContext.get());
            result = throwable;
        }

        log.info("Controller process time : {}(ms), UUID : {}", System.currentTimeMillis() - timer, UuidContext.get());

        if (result instanceof Throwable)
            throw (Throwable) result;
        return result;
    }

    void verifyData(Method method, Object[] args) throws VerifyException {
        if (args == null && args.length == 0)
            return;

        try {
            var parameters = method.getParameters();
            for (var i = 0; i < parameters.length; ++i)
                if (parameters[i].isAnnotationPresent(Verify.class))
                    Validator.staticVerify(args[i]);
        } catch (AssertException e) {
            throw new VerifyException(e.getMessage());
        }
    }

    void verifyAuth(Method method) throws AuthorityException {
        Auth auth = method.getAnnotation(Auth.class);
        if (auth == null)
            return;

        UserBasics userBasics = RequestContext.getUserContext().get();
        if (userBasics == null)
            throw new AuthorityException(AuthorityException.ResponseCode.not_login);

        if (!authService.verifyUserAuth(userBasics.getUserId(), auth.code()))
            throw new AuthorityException(AuthorityException.ResponseCode.restrict_access);
    }
}