package com.saturday.auth.service;

import com.saturday.auth.domain.entity.AuthBasics;
import com.saturday.auth.domain.entity.AuthGroup;
import com.saturday.auth.domain.entity.AuthUserGroup;
import com.saturday.auth.enums.AuthBasicsStatus;
import com.saturday.auth.mapper.AuthBasicsMapper;
import com.saturday.auth.mapper.AuthGroupMapper;
import com.saturday.auth.mapper.AuthUserGroupMapper;
import com.saturday.common.annotation.Auth;
import com.saturday.common.annotation.TimePass;
import com.saturday.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthBasicsMapper authBasicsMapper;
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private AuthUserGroupMapper authUserGroupMapper;

    @TimePass
    @Transactional(rollbackFor = Exception.class)
    public void initAuth(List<AuthBasics> authBasicsList) {
        authBasicsMapper.lockTable();

        // 当前所有
        var currentMap = authBasicsList.stream().collect(Collectors.toMap(AuthBasics::getAuthCode, item -> item));
        // 数据库所有
        var dbMap = authBasicsMapper.selectAll().stream().collect(Collectors.toMap(AuthBasics::getAuthCode, item -> item));
        // 数据库失效
        var dbInvalidAuthCodeList = dbMap.values().stream().filter(item -> item.getStatus() == AuthBasicsStatus.invalid).map(AuthBasics::getAuthCode).collect(Collectors.toList());
        // 交集
        var intersection = new HashSet<>(currentMap.keySet()) {{retainAll(dbMap.keySet());}};

        // 数据库状态失效 当前有 -> 禁用
        dbInvalidAuthCodeList.retainAll(intersection);
        if (!dbInvalidAuthCodeList.isEmpty())
            authBasicsMapper.updateStatusByAuthCode(dbInvalidAuthCodeList, AuthBasicsStatus.disable);

        // 数据库有 当前没有 -> 失效
        dbMap.keySet().removeAll(intersection);
        if (!dbMap.isEmpty())
            authBasicsMapper.updateStatusByAuthCode(new ArrayList<>(dbMap.keySet()), AuthBasicsStatus.invalid);

        // 当前有 数据库没有 -> 新增
        currentMap.keySet().removeAll(intersection);
        if (!currentMap.isEmpty()) {
            currentMap.values().forEach(item -> item.setStatus(AuthBasicsStatus.disable));
            authBasicsMapper.insertAuthBasics(new ArrayList<>(currentMap.values()));
        }

        authBasicsMapper.unlockTable();
    }

    @TimePass
    @Transactional(rollbackFor = Exception.class)
    public void createGroup(AuthGroup authGroup) {
        int rgt = authGroupMapper.selectAuthGroupMaxRgt(authGroup.getPid());
        authGroup.setLft(rgt);
        authGroup.setRgt(rgt + 1);
        authGroupMapper.addLftAndRgt(rgt);
        authGroupMapper.insert(authGroup);
    }

    @TimePass
    @Transactional(rollbackFor = Exception.class)
    public void delGroup(String groupId) {
        AuthGroup authGroup = authGroupMapper.selectByPrimaryKey(groupId);
//        authGroup.setLft(rgt);
//        authGroup.setRgt(rgt + 1);
//        authGroupMapper.addLftAndRgt(rgt);
//        authGroupMapper.insert(authGroup);
    }

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
}