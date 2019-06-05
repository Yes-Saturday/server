package com.saturday.auth.service;

import com.saturday.auth.domain.entity.AuthBasics;
import com.saturday.auth.domain.entity.AuthUserGroup;
import com.saturday.auth.domain.info.AuthInfo;
import com.saturday.auth.mapper.AuthBasicsMapper;
import com.saturday.auth.mapper.AuthGroupMapper;
import com.saturday.auth.mapper.AuthUserGroupMapper;
import com.saturday.common.annotation.Auth;
import com.saturday.common.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private AuthBasicsMapper authBasicsMapper;
    @Autowired
    private AuthUserGroupMapper authUserGroupMapper;

    public List<String> queryAuthListById(String id) {
        var example = Example.builder(AuthBasics.class).selectDistinct("authCode").where(Sqls.custom().andEqualTo("id", id)).build();
        return authBasicsMapper.selectByExample(example).stream().map(AuthBasics::getAuthCode).collect(Collectors.toList());
    }

    public boolean verifyUserAuth(String userId, String authCode) {
        var example = Example.builder(AuthUserGroup.class).selectDistinct("groupId").where(Sqls.custom().andEqualTo("userId", userId)).build();
        var groupList = authUserGroupMapper.selectByExample(example);

        var idList = groupList.stream().map(AuthUserGroup::getGroupId).collect(Collectors.toList());
        idList.add(userId);
        example = Example.builder(AuthBasics.class).andWhere(Sqls.custom().andEqualTo("authCode", authCode).andIn("id", idList)).build();
        return authBasicsMapper.selectCountByExample(example) > 0;
    }

    private final static Set<AuthInfo> authSet = new HashSet<>();
    private final static Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void loadAllAuth() {
        var start = System.currentTimeMillis();
        var controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (var value : controllers.values()) {
            var clazz = value.getClass().getSuperclass();

            var methods = clazz.getDeclaredMethods();
            if (methods == null || methods.length == 0)
                continue;

            for (var method : methods) {
                Auth auth = method.getAnnotation(Auth.class);
                if (auth != null && !authSet.add(new AuthInfo(auth.name(), auth.code())))
                    throw new InternalException("auth_code repeat -> [" + auth.code() + "]");
            }
        }
        log.info("PostConstruct process end, method -> [loadAllAuth], time : {}(ms)", System.currentTimeMillis() - start);
    }
}