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

    public Set<AuthInfo> getAllTypeAuth() {
        return typeAuthSet;
    }

    public Set<AuthInfo> getMethodAuthByTypeCode(String code) {
        return methodAuthMap.get(code);
    }

    public Map<String, Set<AuthInfo>> getAllMethodAuth() {
        return methodAuthMap;
    }

    private final static Set<AuthInfo> typeAuthSet = new HashSet<>();
    private final static Map<String, Set<AuthInfo>> methodAuthMap = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void loadAllAuth() {
        var start = System.currentTimeMillis();
        var controllers = applicationContext.getBeansWithAnnotation(Auth.class);
        for (var value : controllers.values()) {
            var clazz = value.getClass().getSuperclass();
            var typeAuth = clazz.getAnnotation(Auth.class);

            var methods = clazz.getDeclaredMethods();
            if (methods == null || methods.length == 0)
                continue;

            Set<AuthInfo> methodAuthSet = new HashSet<>();

            for (var method : methods) {
                Auth auth = method.getAnnotation(Auth.class);
                if (auth != null && !methodAuthSet.add(new AuthInfo(auth.name(), auth.code())))
                    throw new InternalException("auth_code repeat -> [" + auth.code() + "]");
            }

            if (methodAuthSet.size() > 0)
                if (typeAuthSet.add(new AuthInfo(typeAuth.name(), typeAuth.code())))
                    methodAuthMap.put(typeAuth.code(), methodAuthSet);
                else
                    throw new InternalException("auth_code repeat -> [" + typeAuth.code() + "]");
        }
        log.info("PostConstruct process end, method -> [loadAllAuth], time : {}(ms)", System.currentTimeMillis() - start);
    }
}