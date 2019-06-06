package com.saturday.web.config;

import com.saturday.auth.domain.entity.AuthBasics;
import com.saturday.auth.service.AuthService;
import com.saturday.common.annotation.Auth;
import com.saturday.common.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;

@Slf4j
@Component
public class WebAppInit implements ApplicationListener<ApplicationContextEvent> {
    @Autowired
    private AuthService authService;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationEvent) {
        var applicationContext = applicationEvent.getApplicationContext();
        var authSet = new HashSet<AuthBasics>();

        var start = System.currentTimeMillis();
        var controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (var value : controllers.values()) {
            var clazz = value.getClass().getSuperclass();

            var methods = clazz.getDeclaredMethods();
            if (methods == null || methods.length == 0)
                continue;

            for (var method : methods) {
                Auth auth = method.getAnnotation(Auth.class);
                if (auth != null && !authSet.add(new AuthBasics(auth.value(), auth.name())))
                    throw new InternalException("auth_code repeat -> [" + auth.value() + "]");
            }
        }

        authService.initAuth(new ArrayList<>(authSet));

        log.info("PostConstruct process end, method -> [loadAllAuth], time : {}(ms)", System.currentTimeMillis() - start);
    }
}